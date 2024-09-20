package controller;

import com.egar.library.model.AuthorDTO;
import com.egar.library.rest.AuthorResource;
import com.egar.library.service.AuthorService;
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

public class AuthorResourceTest {

    @InjectMocks
    private AuthorResource authorResource;

    @Mock
    private AuthorService authorService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAuthors() {
        AuthorDTO author1 = new AuthorDTO(2L, "Author One", "test description");
        AuthorDTO author2 = new AuthorDTO(2L, "Author Two", "test description2");
        List<AuthorDTO> authors = Arrays.asList(author1, author2);
        when(authorService.findAll()).thenReturn(authors);

        ResponseEntity<List<AuthorDTO>> response = authorResource.getAllAuthors();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authors, response.getBody());
        verify(authorService).findAll();
    }

    @Test
    public void testGetAuthor() {
        Long authorId = 1L;
        AuthorDTO author = new AuthorDTO(1L, "Author One", "test description");
        when(authorService.getById(authorId)).thenReturn(author);

        ResponseEntity<AuthorDTO> response = authorResource.getAuthor(authorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(author, response.getBody());
        verify(authorService).getById(authorId);
    }

    @Test
    public void testCreateAuthor() {
        AuthorDTO authorDTO = new AuthorDTO(1L, "Author One", "test description");

        ResponseEntity<Long> response = authorResource.createAuthor(authorDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(authorService).create(authorDTO);
    }

    @Test
    public void testUpdateAuthor() {
        Long authorId = 1L;
        AuthorDTO authorDTO = new AuthorDTO(authorId, "Updated Author", "test description");

        doNothing().when(authorService).update(eq(authorId), any(AuthorDTO.class));

        ResponseEntity<Long> response = authorResource.updateAuthor(authorId, authorDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authorId, response.getBody());
        verify(authorService).update(authorId, authorDTO);
    }


    @Test
    public void testDeleteAuthor() {
        Long authorId = 1L;
        ResponseEntity<Long> response = authorResource.deleteAuthor(authorId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(authorService).delete(authorId);
    }
}