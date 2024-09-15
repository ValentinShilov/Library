package com.egar.library.web.handler;

import com.egar.library.exceptions.AlreadyExitsException;
import com.egar.library.exceptions.EntityNotFound;
import com.egar.library.exceptions.RefreshTokenException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class WebAppExceptionHandler {
    @ExceptionHandler(value = RefreshTokenException.class)
    public ResponseEntity<ErrorResponseBody> refreshTokenExceptionHandler(RefreshTokenException ex, WebRequest request) {
        return buildResponse(HttpStatus.FORBIDDEN, ex, request);
    }

    @ExceptionHandler(value = AlreadyExitsException.class)
    public ResponseEntity<ErrorResponseBody> alreadyExistsBody(AlreadyExitsException ex, WebRequest request){
        return buildResponse(HttpStatus.BAD_REQUEST, ex, request);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseBody> notFoundException(EntityNotFoundException ex, WebRequest request){
        return buildResponse(HttpStatus.NOT_FOUND, ex, request);
    }

        private ResponseEntity<ErrorResponseBody> buildResponse(HttpStatus httpStatus, Exception ex, WebRequest webRequest) {
        return ResponseEntity.status(httpStatus)
                .body(ErrorResponseBody.builder()
                        .message(ex.getMessage())
                        .description(webRequest.getDescription(false))
                        .build());
    }
}
