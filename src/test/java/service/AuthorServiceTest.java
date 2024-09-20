package service;

import com.egar.library.entity.Author;
import com.egar.library.entity.Book;
import com.egar.library.model.AuthorDTO;
import com.egar.library.repos.AuthorRepository;
import com.egar.library.repos.BookRepository;
import com.egar.library.exceptions.NotFoundException;
import com.egar.library.service.AuthorService;
import com.egar.library.util.ReferencedWarning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        author = new Author();
        author.setId(1L);
        author.setName("Test Author");
        author.setDescription("Test Description");
    }

    @Test
    public void testGetByIdFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        AuthorDTO authorDTO = authorService.getById(1L);

        assertNotNull(authorDTO);
        assertEquals("Test Author", authorDTO.getName());
    }

    @Test
    public void testGetByIdNotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> authorService.getById(1L));
    }

    @Test
    public void testCreate() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("New Author");
        authorDTO.setDescription("New Description");

        authorService.create(authorDTO);

        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    public void testUpdateFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("Updated Author");
        authorDTO.setDescription("Updated Description");

        authorService.update(1L, authorDTO);

        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    public void testUpdateNotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName("Updated Author");
        authorDTO.setDescription("Updated Description");

        assertThrows(NotFoundException.class, () -> authorService.update(1L, authorDTO));
    }

    @Test
    public void testDelete() {
        doNothing().when(authorRepository).deleteById(1L);

        authorService.delete(1L);

        verify(authorRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetReferencedWarningAuthorHasBook() {
        Book book = new Book();
        book.setId(1L);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.findFirstByAuthor(author)).thenReturn(book);

        ReferencedWarning referencedWarning = authorService.getReferencedWarning(1L);

        assertNotNull(referencedWarning);
        assertEquals("author.book.author.referenced", referencedWarning.getKey());
        assertEquals(1L, referencedWarning.getParams().get(0));
    }

    @Test
    public


    void testGetReferencedWarningAuthorHasNoBooks() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.findFirstByAuthor(author)).thenReturn(null);

        ReferencedWarning referencedWarning = authorService.getReferencedWarning(1L);

        assertNull(referencedWarning);
    }

    @Test
    public void testGetReferencedWarningNotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> authorService.getReferencedWarning(1L));
    }
}