package com.egar.library.service;

import com.egar.library.entity.Role;
import com.egar.library.entity.User;
import com.egar.library.repos.UserRepository;

import java.util.Collections;

import com.egar.library.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public User createNewAccount(User user, Role role){
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already taken");
        }
        user.setRoles(Collections.singleton(role.getAuthority()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        role.setUser(user);

        log.info("Creating new user: {}", user.getUsername());
        return userRepository.save(user);
    }



    public User findByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }
}
