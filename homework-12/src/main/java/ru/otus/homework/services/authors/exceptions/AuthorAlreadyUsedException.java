package ru.otus.homework.services.authors.exceptions;

public class AuthorAlreadyUsedException extends RuntimeException {
    public AuthorAlreadyUsedException(String message) {
        super(message);
    }
}
