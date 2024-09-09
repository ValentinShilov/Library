package com.egar.library.controller;

import com.egar.library.model.GenreDTO;
import com.egar.library.service.GenreService;
import com.egar.library.util.ReferencedWarning;
import com.egar.library.util.WebUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
@RequestMapping("/genres")
@AllArgsConstructor

public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("genres", genreService.findAll());
        return "genre/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("genre") final GenreDTO genreDTO) {
        return "genre/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("genre") @Valid final GenreDTO genreDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "genre/add";
        }
        genreService.create(genreDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("genre.create.success"));
        return "redirect:/genres";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("genre", genreService.getById(id));
        return "genre/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("genre") @Valid final GenreDTO genreDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "genre/edit";
        }
        genreService.update(id, genreDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("genre.update.success"));
        return "redirect:/genres";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = genreService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            genreService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("genre.delete.success"));
        }
        return "redirect:/genres";
    }

}
