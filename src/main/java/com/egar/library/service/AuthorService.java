package com.egar.library.service;

import com.egar.library.entity.Author;
import com.egar.library.entity.Book;
import com.egar.library.model.AuthorDTO;
import com.egar.library.repos.AuthorRepository;

import java.util.List;

import com.egar.library.repos.BookRepository;
import com.egar.library.exceptions.NotFoundException;
import com.egar.library.util.ReferencedWarning;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class AuthorService implements CRUDService<AuthorDTO> {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Override
    public List<AuthorDTO> findAll() {
        log.info("Fetching all authors");
        List<Author> authors = authorRepository.findAll(Sort.by("id"));
        return authors.stream()
                .map(author -> mapToDTO(author, new AuthorDTO()))
                .toList();
    }

    @Override
    public AuthorDTO getById(Long id) {
        log.info("Fetching author with id: {}", id);
        return authorRepository.findById(id)
                .map(author -> mapToDTO(author, new AuthorDTO()))
                .orElseThrow();
    }

    @Override
    public void create(AuthorDTO authorDTO) {
        log.info("Creating new author: {}", authorDTO);
        authorRepository.save(mapToEntity(authorDTO, new Author()));
    }

    @Override
    public void update(Long id, AuthorDTO authorDTO) {
        log.info("Updating author");
        Author author = authorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        authorRepository.save(mapToEntity(authorDTO, author));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting author with id: {}", id);
        authorRepository.deleteById(id);
    }


    private AuthorDTO mapToDTO(final Author author, final AuthorDTO authorDTO) {
        authorDTO.setId(author.getId());
        authorDTO.setName(author.getName());
        authorDTO.setDescription((author.getDescription()));
        return authorDTO;
    }

    private Author mapToEntity(final AuthorDTO authorDTO, final Author author) {
        author.setName(authorDTO.getName());
        author.setDescription(authorDTO.getDescription());
        return author;
    }
    public ReferencedWarning getReferencedWarning(Long id) {
        ReferencedWarning referencedWarning = new ReferencedWarning();
        Author author = authorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        Book authorBook = bookRepository.findFirstByAuthor(author);
        if (authorBook != null) {
            referencedWarning.setKey("author.book.author.referenced");
            referencedWarning.addParam(authorBook.getId());
            return referencedWarning;
        }
        return null;
    }
}
