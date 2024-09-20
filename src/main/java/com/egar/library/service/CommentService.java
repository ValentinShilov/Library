package com.egar.library.service;

import com.egar.library.entity.Book;
import com.egar.library.entity.Comment;
import com.egar.library.entity.User;
import com.egar.library.model.CommentDTO;
import com.egar.library.repos.BookRepository;
import com.egar.library.repos.CommentRepository;
import com.egar.library.repos.UserRepository;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class CommentService implements CRUDService<CommentDTO> {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    public List<CommentDTO> findAll() {
        log.info("Fetching all comments ");
        List<Comment> comments = commentRepository.findAll(Sort.by("id"));
        return comments.stream()
                .map(comment -> mapToDTO(comment, new CommentDTO()))
                .toList();
    }
    public List<CommentDTO> getCommentsByBookId(Long bookId) {
        List<Comment>comments =  commentRepository.findByBookId(bookId);
        return comments.stream()
                .map(comment -> mapToDTO(comment, new CommentDTO()))
                .toList();
    }


    @Override
    public CommentDTO getById(Long id) {
        log.info("Fetching comment with id: {}", id);
        return commentRepository.findById(id)
                .map(comment -> mapToDTO(comment, new CommentDTO()))
                .orElseThrow();
    }

    public void create(final CommentDTO commentDTO) {
        log.info("Creating new comment: {}", commentDTO);

        Comment comment = new Comment();
        mapToEntity(commentDTO, comment);

        Book book = bookRepository.findById(commentDTO.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        comment.setBook(book);

        commentRepository.save(comment);
    }

    @Override
    public void update(Long id, CommentDTO commentDTO) {
        log.info("Updating comment with id: {}", id);
        Comment comment = commentRepository.findById(id)
                .orElseThrow();
        mapToEntity(commentDTO, comment);
        commentRepository.save(comment);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting comment with id: {}", id);
        commentRepository.deleteById(id);
    }

    private CommentDTO mapToDTO(Comment comment, CommentDTO commentDTO) {
        commentDTO.setId(comment.getId());
        commentDTO.setText(comment.getText());
        commentDTO.setUserId(comment.getUser() == null ? null : comment.getUser().getId());
        commentDTO.setBookId(comment.getBook() == null ? null : comment.getBook().getId());
        return commentDTO;
    }

    private Comment mapToEntity(CommentDTO commentDTO, Comment comment) {
        comment.setId(commentDTO.getId());
        comment.setText(commentDTO.getText());
        User user = commentDTO.getUserId() == null ? null : userRepository.findById(commentDTO
                        .getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        comment.setUser(user);
        Book book = bookRepository.findById(commentDTO.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        comment.setBook(book);
        return comment;
    }

}
