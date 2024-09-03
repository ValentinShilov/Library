package com.egar.library.service;

import com.egar.library.entity.Genre;
import com.egar.library.model.GenreDTO;
import com.egar.library.repos.GenreRepository;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class GenreService implements CRUDService<GenreDTO> {

    private final GenreRepository genreRepository;

    @Override
    public List<GenreDTO> findAll() {
        log.info("Fetching all genres");
        List<Genre> genres = genreRepository.findAll(Sort.by("id"));
        return genres.stream()
                .map(genre -> mapToDTO(genre, new GenreDTO()))
                .toList();
    }

    @Override
    public GenreDTO getById(Long id) {
        log.info("Fetching genre with id: {}", id);
        return genreRepository.findById(id)
                .map(genre -> mapToDTO(genre, new GenreDTO()))
                .orElseThrow();
    }

    @Override
    public void create(GenreDTO genreDTO) {
        log.info("Creating new genre: {}", genreDTO);
        Genre genre = new Genre();
        mapToEntity(genreDTO, genre);
        genreRepository.save(genre);
    }

    @Override
    public void update(Long id, GenreDTO genreDTO) {
        log.info("Updating genre with id: {}", id);
        Genre genre = genreRepository.findById(id)
                .orElseThrow();
        mapToEntity(genreDTO, genre);
        genreRepository.save(genre);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting genre with id: {}", id);
        genreRepository.deleteById(id);
    }

    private GenreDTO mapToDTO(Genre genre, GenreDTO genreDTO) {
        genreDTO.setId(genre.getId());
        genreDTO.setName(genre.getName());
        return genreDTO;
    }

    private Genre mapToEntity(GenreDTO genreDTO, Genre genre) {
        genre.setId(genreDTO.getId());
        genre.setName(genreDTO.getName());
        return genre;
    }
}
