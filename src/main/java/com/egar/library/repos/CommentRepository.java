package com.egar.library.repos;

import com.egar.library.entity.Book;
import com.egar.library.entity.Comment;
import com.egar.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findFirstByBook(Book book);
    List<Comment> findByBookId(Long bookId);

}
