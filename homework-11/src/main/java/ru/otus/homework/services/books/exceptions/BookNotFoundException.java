package ru.otus.homework.services.books.exceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException() {
        super("Book not found");
    }
}
