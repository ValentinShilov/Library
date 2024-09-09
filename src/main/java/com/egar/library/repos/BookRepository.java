package com.egar.library.repos;

import com.egar.library.entity.Author;
import com.egar.library.entity.Book;
import com.egar.library.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long> {

    Book findFirstByAuthor(Author author);

    Book findFirstByGenre(Genre genre);

    List<Book> findByNameContainingIgnoreCase(String name);

}
