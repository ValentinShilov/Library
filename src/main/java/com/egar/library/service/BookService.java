package com.egar.library.service;

import com.egar.library.entity.Author;
import com.egar.library.entity.Book;
import com.egar.library.entity.Genre;
import com.egar.library.model.BookDTO;
import com.egar.library.repos.AuthorRepository;
import com.egar.library.repos.BookRepository;
import com.egar.library.repos.GenreRepository;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.exec.ExecutionException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class BookService implements CRUDService<BookDTO> {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Override
    public List<BookDTO> findAll() {
        log.info("Fetching all books");
        List<Book> books = bookRepository.findAll(Sort.by("id"));
        return books.stream()
                .map(book -> mapToDTO(book, new BookDTO()))
                .toList();
    }

    @Override
    public BookDTO getById(final Long id) {
        log.info("Fetching book with id: {}", id);
        return bookRepository.findById(id)
                .map(book -> mapToDTO(book, new BookDTO()))
                .orElseThrow();
    }

    @Override
    public void create(BookDTO bookDTO) {
        log.info("Creating new book: {}", bookDTO);
        Book book = new Book();
        mapToEntity(bookDTO, book);
        bookRepository.save(book);
    }

    @Override
    public void update(Long id, BookDTO bookDTO) {
        log.info("Updating book with id: {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow();
        mapToEntity(bookDTO, book);
        bookRepository.save(book);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting book with id: {}", id);
        bookRepository.deleteById(id);
    }

    private BookDTO mapToDTO(Book book, BookDTO bookDTO) {
        bookDTO.setId(book.getId());
        bookDTO.setName(book.getName());
        bookDTO.setAuthorId(book.getAuthor() == null ? null : book.getAuthor().getId());
        bookDTO.setGenreId(book.getGenre() == null ? null : book.getGenre().getId());
        return bookDTO;
    }

    private Book mapToEntity(BookDTO bookDTO, Book book) {
        book.setId(bookDTO.getId());
        book.setName(bookDTO.getName());
        Author author = bookDTO.getAuthorId() == null ? null : authorRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(() -> new ExecutionException("author not found"));
        book.setAuthor(author);
        Genre genre = bookDTO.getGenreId() == null ? null : genreRepository.findById(bookDTO.getGenreId())
                .orElseThrow(() -> new ExecutionException("genre not found"));
        book.setGenre(genre);
        return book;
    }

}
