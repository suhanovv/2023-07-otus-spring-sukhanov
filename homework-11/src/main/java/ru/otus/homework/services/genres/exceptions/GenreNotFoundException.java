package ru.otus.homework.services.genres.exceptions;

public class GenreNotFoundException extends RuntimeException {
    public GenreNotFoundException(String message) {
        super(message);
    }

    public GenreNotFoundException() {
        super("Genre not found");
    }
}
