package ru.otus.homework.services.books.exceptions;

public class CreateBookException extends RuntimeException {
    public CreateBookException(Throwable cause) {
        super(cause);
    }
}
