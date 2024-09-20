package service;

import com.egar.library.entity.Author;
import com.egar.library.entity.Book;
import com.egar.library.entity.Comment;
import com.egar.library.exceptions.NotFoundException;
import com.egar.library.model.BookDTO;
import com.egar.library.repos.AuthorRepository;
import com.egar.library.repos.BookRepository;
import com.egar.library.repos.CommentRepository;
import com.egar.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private BookService bookService;

    private Author author;
    private Book book;
    private BookDTO bookDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        author = new Author();
        author.setId(1L);
        author.setName("Author Name");

        book = new Book();
        book.setId(1L);
        book.setName("Book Name");
        book.setAuthor(author);

        bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setName("Book Name");
        bookDTO.setAuthor(1L);
    }


    @Test
    void testGetByIdSuccess() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        var result = bookService.getById(1L);

        assertEquals("Book Name", result.getName());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> bookService.getById(1L));
    }

    @Test
    void testCreate() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        bookService.create(bookDTO);

        ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository, times(1)).save(bookCaptor.capture());
        assertEquals("Book Name", bookCaptor.getValue().getName());
        assertEquals(author.getId(), bookCaptor.getValue().getAuthor().getId());
    }

    @Test
    void testUpdate() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        bookService.update(1L, bookDTO);

        verify(bookRepository, times(1)).save(book);
        assertEquals("Book Name", book.getName());
    }

    @Test
    void testDelete() {
        bookService.delete(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetReferencedWarning() {
        Comment comment = new Comment();
        comment.setId(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(commentRepository.findFirstByBook(book)).thenReturn(comment);

        var warning = bookService.getReferencedWarning(1L);

        assertNotNull(warning);
        assertEquals("book.comment.book.referenced", warning.getKey());
        assertEquals(1L, warning.getParams().get(0));
    }

    @Test
    void testGetReferencedWarningBookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.getReferencedWarning(1L));
    }


}