package ru.otus.homework.services.books.exceptions;

public class BookAlreadyUsedException extends Exception {
    public BookAlreadyUsedException(String message) {
        super(message);
    }
}
