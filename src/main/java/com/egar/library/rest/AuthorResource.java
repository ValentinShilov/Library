package com.egar.library.rest;

import com.egar.library.model.AuthorDTO;
import com.egar.library.service.AuthorService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/authors")
@AllArgsConstructor

public class AuthorResource {

    private final AuthorService authorService;


    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        return ResponseEntity.ok(authorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthor(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(authorService.getById(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        authorService.create(authorDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateAuthor(@PathVariable(name = "id") Long id,
            @RequestBody @Valid AuthorDTO authorDTO) {
        authorDTO.setId(id);
        authorService.update(id, authorDTO);
        return ResponseEntity.ok(id);
    }
    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Long> deleteAuthor(@PathVariable Long id){
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
