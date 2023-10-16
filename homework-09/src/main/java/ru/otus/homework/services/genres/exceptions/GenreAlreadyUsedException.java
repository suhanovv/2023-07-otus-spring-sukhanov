package ru.otus.homework.services.genres.exceptions;

public class GenreAlreadyUsedException extends RuntimeException {
    public GenreAlreadyUsedException(String message) {
        super(message);
    }
}
