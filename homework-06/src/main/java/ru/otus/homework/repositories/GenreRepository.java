package ru.otus.homework.repositories;

import ru.otus.homework.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    Genre insert(Genre genre);

    Genre update(Genre genre);

    Optional<Genre> getById(long id);

    List<Genre> getAll();

    void delete(Genre genre);
}
