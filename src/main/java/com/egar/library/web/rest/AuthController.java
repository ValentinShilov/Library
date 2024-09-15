package com.egar.library.web.rest;

import com.egar.library.exceptions.AlreadyExitsException;
import com.egar.library.repos.UserRepository;
import com.egar.library.security.SecurityService;
import com.egar.library.web.model.AuthResponse;
import com.egar.library.web.model.CreateUserRequest;
import com.egar.library.web.model.LoginRequest;
import com.egar.library.web.model.RefreshTokenRequest;
import com.egar.library.web.model.RefreshTokenResponse;
import com.egar.library.web.model.SimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final SecurityService securityService;

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> authUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(securityService.authUser(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<SimpleResponse> registerUser(@RequestBody CreateUserRequest createUserRequest){
        System.out.println("попал");
        if (userRepository.existsByUsername(createUserRequest.getUsername())){
            throw new AlreadyExitsException("Username already exists");
        }
        if (userRepository.existsByEmail(createUserRequest.getEmail())){
            throw new AlreadyExitsException("Email already exists");
        }
        securityService.register(createUserRequest);

        return ResponseEntity.ok(new SimpleResponse("User created!"));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request){
        return ResponseEntity.ok(securityService.refreshToken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<SimpleResponse> logout(@AuthenticationPrincipal UserDetails userDetails){
        securityService.logout();
        return ResponseEntity.ok(new SimpleResponse("User Logout. username is: " + userDetails.getUsername()));
    }

}
