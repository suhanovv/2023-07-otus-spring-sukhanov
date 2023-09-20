package ru.otus.homework.services.authors.exceptions;

public class AuthorAlreadyUsedException extends Exception {
    public AuthorAlreadyUsedException(String message) {
        super(message);
    }
}
