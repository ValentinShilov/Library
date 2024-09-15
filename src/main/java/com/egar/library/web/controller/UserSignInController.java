package com.egar.library.web.controller;

import com.egar.library.repos.UserRepository;
import com.egar.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/signin")
@RequiredArgsConstructor
public class UserSignInController {

    private final UserService userService;

    @GetMapping
    public String showLoginForm() {
        return "login";
    }

    @PostMapping
    public String login(@RequestParam String username, @RequestParam String password) {
            return "redirect:/";
    }

}
