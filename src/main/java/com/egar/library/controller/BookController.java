package com.egar.library.controller;

import com.egar.library.model.CommentDTO;
import com.egar.library.service.BookService;
import com.egar.library.entity.Author;
import com.egar.library.model.BookDTO;
import com.egar.library.repos.AuthorRepository;
import com.egar.library.service.CommentService;
import com.egar.library.util.CustomCollectors;
import com.egar.library.util.ReferencedWarning;
import com.egar.library.util.WebUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@AllArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorRepository authorRepository;
    private final CommentService commentService;

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("authorValues", authorRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Author::getId, Author::getId)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("books", bookService.findAll());
        return "book/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("book") final BookDTO bookDTO) {
        return "book/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("book") @Valid final BookDTO bookDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "book/add";
        }
        bookService.create(bookDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("book.create.success"));
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("book", bookService.getById(id));
        return "book/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("book") @Valid final BookDTO bookDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "book/edit";
        }
        bookService.update(id, bookDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("book.update.success"));
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "name", required = false, defaultValue = "") String name, Model model) {
        if (name.isEmpty()) {
            model.addAttribute("books", bookService.findAll());
        } else {
            model.addAttribute("books", bookService.findByName(name));
        }
        return "book/search";
    }
    @GetMapping("/filter")
    public String searchBooks(@RequestParam(required = false) String query,
                              @RequestParam(required = false) String sort,
                              Model model) {
        List<BookDTO> books = bookService.filterAndSortBooks(query, sort);

        model.addAttribute("books", books);
        return "book/list";
    }
    @GetMapping("/{id}/comments")
    public String getCommentsByBookId(@PathVariable(name = "id") Long id, Model model) {
        BookDTO book = bookService.getById(id);
        List<CommentDTO> comments = commentService.getCommentsByBookId(id);

        model.addAttribute("book", book);
        model.addAttribute("comments", comments);

        return "book/comment";
    }




    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") Long id,
                         RedirectAttributes redirectAttributes) {
        ReferencedWarning referencedWarning = bookService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            bookService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("book.delete.success"));
        }
        return "redirect:/books";
    }

}
