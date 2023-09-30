package ru.otus.homework.repositories;

import ru.otus.homework.models.Author;
import ru.otus.homework.models.Genre;


public interface BookRepositoryCustom {
    void updateBooksAuthorByAuthorId(String authorId, Author author);

    void updateBooksGenreByGenreId(String genreId, Genre genre);
}

