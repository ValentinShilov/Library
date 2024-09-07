package com.egar.library.rest;

import com.egar.library.model.GenreDTO;
import com.egar.library.service.GenreService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@AllArgsConstructor
@RequestMapping(value = "/api/genres", produces = MediaType.APPLICATION_JSON_VALUE)
public class GenreResource {

    private final GenreService genreService;

    @GetMapping
    public ResponseEntity<List<GenreDTO>> getAllGenres() {
        return ResponseEntity.ok(genreService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDTO> getGenre(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(genreService.getById(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createGenre(@RequestBody @Valid GenreDTO genreDTO) {
        genreService.create(genreDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateGenre(@PathVariable(name = "id") Long id,
            @RequestBody @Valid GenreDTO genreDTO) {
        genreDTO.setId(id);
        genreService.update(id, genreDTO);
        return ResponseEntity.ok(id);
    }
    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id){
        genreService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
