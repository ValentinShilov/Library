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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("comments", commentService.findAll());
        return "comment/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("comment") final CommentDTO commentDTO) {
        return "comment/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("comment") @Valid final CommentDTO commentDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "comment/add";
        }
        commentService.create(commentDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("comment.create.success"));
        return "redirect:/comments";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("comment", commentService.getById(id));
        return "comment/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("comment") @Valid final CommentDTO commentDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "comment/edit";
        }
        commentService.update(id, commentDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("comment.update.success"));
        return "redirect:/comments";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        commentService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("comment.delete.success"));
        return "redirect:/comments";
    }

}
