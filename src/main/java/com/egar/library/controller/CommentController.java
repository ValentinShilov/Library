package com.egar.library.controller;

import com.egar.library.entity.Book;
import com.egar.library.entity.User;
import com.egar.library.model.CommentDTO;
import com.egar.library.repos.BookRepository;
import com.egar.library.repos.UserRepository;
import com.egar.library.service.CommentService;
import com.egar.library.util.CustomCollectors;
import com.egar.library.util.WebUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/comments")
@AllArgsConstructor

public class CommentController {

    private final CommentService commentService;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;


    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("userValues", userRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(User::getId, User::getId)));
        model.addAttribute("bookValues", bookRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Book::getId, Book::getId)));
    }

    @PostMapping("/add")
    public String createComment(@ModelAttribute CommentDTO commentDTO) {
        commentService.create(commentDTO);
        return String.format("redirect:/books/%d/comments", commentDTO.getBookId());
    }


}
