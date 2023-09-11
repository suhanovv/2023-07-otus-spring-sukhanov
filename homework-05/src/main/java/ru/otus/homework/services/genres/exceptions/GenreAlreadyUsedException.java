package ru.otus.homework.services.genres.exceptions;

public class GenreAlreadyUsedException extends Exception {
    public GenreAlreadyUsedException(String message) {
        super(message);
    }
}
