package ru.otus.homework.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.otus.homework.controllers.exceptions.AuthorNotFoundException;
import ru.otus.homework.controllers.exceptions.BookNotFoundException;
import ru.otus.homework.controllers.exceptions.GenreNotFoundException;

import java.util.HashMap;
import java.util.Map;

//
@ControllerAdvice
public class ExceptionHandlerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler({BookNotFoundException.class, AuthorNotFoundException.class, GenreNotFoundException.class})
    public ResponseEntity<?> handleNotFoundError() {
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<?> notValid(WebExchangeBindException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        return new ResponseEntity<>(Mono.just(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleExceptions(ServerWebExchange exchange, Exception ex) {
        LOGGER.error("Request: " + exchange.getRequest().getPath().value() + " raised " + ex);
        return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
