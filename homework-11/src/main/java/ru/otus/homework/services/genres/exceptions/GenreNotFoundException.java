package ru.otus.homework.services.genres.exceptions;

public class GenreNotFoundException extends RuntimeException {

    public GenreNotFoundException() {
        super("Genre not found");
    }
}
