package com.egar.library.service;

import com.egar.library.entity.Author;
import com.egar.library.entity.Book;
import com.egar.library.entity.Comment;
import com.egar.library.entity.Genre;
import com.egar.library.model.BookDTO;
import com.egar.library.repos.AuthorRepository;
import com.egar.library.repos.BookRepository;
import com.egar.library.repos.CommentRepository;
import com.egar.library.repos.GenreRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.egar.library.util.NotFoundException;
import com.egar.library.util.ReferencedWarning;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.exec.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class BookService implements CRUDService<BookDTO> {
    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final AuthorRepository authorRepository;
    @Autowired
    private final GenreRepository genreRepository;
    @Autowired
    private final CommentRepository commentRepository;

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
    public List<BookDTO> findByName(String name) {
        List<Book> books = bookRepository.findByNameContainingIgnoreCase(name);
        return books.stream()
                .map(book -> mapToDTO(book, new BookDTO()))
                .toList();
    }
    @Override
    public void create(BookDTO bookDTO) {
        log.info("Creating new book: {}", bookDTO);
        Book book = new Book();
        Long id = bookDTO.getAuthorId();
        Author author = authorRepository.findById(id).orElseThrow();
        mapToEntity(bookDTO, book);
        book.setAuthor(author);
        bookRepository.save(book);
    }

    @Override
    public void update(Long id, BookDTO bookDTO) {
        log.info("Updating book with id: {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow();
        Long authorId = bookDTO.getAuthorId();
        Author author = authorRepository.findById(authorId).orElseThrow();
        book.setAuthor(author);
        mapToEntity(bookDTO, book);
        bookRepository.save(book);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting book with id: {}", id);
        bookRepository.deleteById(id);
    }

    private BookDTO mapToDTO(final Book book, final BookDTO bookDTO) {
        bookDTO.setId(book.getId());
        bookDTO.setName(book.getName());
        bookDTO.setAuthorId(book.getAuthor() == null ? null : book.getAuthor().getId());
        bookDTO.setGenreId(book.getGenre() == null ? null : book.getGenre().getId());
        return bookDTO;
    }

    private Book mapToEntity(final BookDTO bookDTO, final Book book) {
        book.setName(bookDTO.getName());
        final Author author = bookDTO.getAuthorId() == null ? null : authorRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(() -> new NotFoundException("author not found"));
        book.setAuthor(author);
        final Genre genre = bookDTO.getGenreId() == null ? null : genreRepository.findById(bookDTO.getGenreId())
                .orElseThrow(() -> new NotFoundException("genre not found"));
        book.setGenre(genre);
        return book;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Book book = bookRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Comment bookComment = commentRepository.findFirstByBook(book);
        if (bookComment != null) {
            referencedWarning.setKey("book.comment.book.referenced");
            referencedWarning.addParam(bookComment.getId());
            return referencedWarning;
        }
        return null;
    }

}
