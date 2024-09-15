package com.egar.library.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("Username " + username + " not found");
    }

}
