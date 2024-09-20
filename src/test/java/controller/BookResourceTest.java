package controller;

import com.egar.library.model.BookDTO;
import com.egar.library.rest.BookResource;
import com.egar.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookResourceTest {

    @InjectMocks
    private BookResource bookResource;

    @Mock
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBooks() {
        BookDTO book1 = new BookDTO(1L, "Book One", 1L);
        BookDTO book2 = new BookDTO(2L, "Book Two", 2L);
        List<BookDTO> books = Arrays.asList(book1, book2);

        when(bookService.findAll()).thenReturn(books);

        ResponseEntity<List<BookDTO>> response = bookResource.getAllBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(books, response.getBody());
        verify(bookService).findAll();
    }

    @Test
    public void testGetBook() {
        Long bookId = 1L;
        BookDTO book = new BookDTO(bookId, "Book One", 1L);
        when(bookService.getById(bookId)).thenReturn(book);

        ResponseEntity<BookDTO> response = bookResource.getBook(bookId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
        verify(bookService).getById(bookId);
    }

    @Test
    public void testCreateBook() {
        BookDTO bookDTO = new BookDTO(null, "New Book", 1L);

        ResponseEntity<Long> response = bookResource.createBook(bookDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(bookService).create(bookDTO);
    }

    @Test
    public void testUpdateBook() {
        Long bookId = 1L;
        BookDTO bookDTO = new BookDTO(bookId, "Updated Book", 1L);
        doNothing().when(bookService).update(eq(bookId), any(BookDTO.class));

        ResponseEntity<Long> response = bookResource.updateBook(bookId, bookDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookId, response.getBody());
        verify(bookService).update(bookId, bookDTO);
    }

    @Test
    public void testDeleteBook() {
        Long bookId = 1L;

        ResponseEntity<Void> response = bookResource.deleteBook(bookId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(bookService).delete(bookId);
    }

    @Test
    public void testSearchBooks() {
        String bookName = "Book One";
        BookDTO book1 = new BookDTO(1L, bookName, 1L);
        List<BookDTO> books = Arrays.asList(book1);
        when(bookService.findByName(bookName)).thenReturn(books);

        List<BookDTO> response = bookResource.search(bookName);

        assertEquals(books, response);
        verify(bookService).findByName(bookName);
    }
}