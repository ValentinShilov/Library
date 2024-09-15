package com.egar.library.web.controller;

import com.egar.library.security.SecurityService;
import com.egar.library.web.model.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/register")
public class UserRegistrationController {
    private final SecurityService securityService;
    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("createUserRequest", new CreateUserRequest());
        return "registration";
    }

    @PostMapping
    public String registerUser(@ModelAttribute CreateUserRequest createUserRequest, Model model) {
         securityService.register(createUserRequest);
        model.addAttribute("message", "User registered successfully!");
        return "redirect:signin";
    }

}
