package ru.otus.homework.services.authors.exceptions;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(String message) {
        super(message);
    }

    public AuthorNotFoundException() {
        super("Author not found");
    }
}
