package ru.otus.homework.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.otus.homework.services.authors.exceptions.AuthorNotFoundException;
import ru.otus.homework.services.books.exceptions.BookNotFoundException;
import ru.otus.homework.services.genres.exceptions.GenreNotFoundException;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class ExceptionHandlerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler({BookNotFoundException.class, AuthorNotFoundException.class, GenreNotFoundException.class})
    public ResponseEntity<?> handleNotFoundError() {
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> notValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        ex.getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleExceptions(Exception ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        LOGGER.error("Request: " + request.getRequestURL() + " raised " + ex);
        errors.put("message", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
