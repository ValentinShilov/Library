package com.egar.library.repos;

import com.egar.library.entity.Author;
import com.egar.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long> {

    Book findFirstByAuthor(Author author);

    List<Book> findByNameContainingIgnoreCase(String name);
    List<Book> findByAuthorId(Long authorId);

}
