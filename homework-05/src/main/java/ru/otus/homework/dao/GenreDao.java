package ru.otus.homework.dao;

import ru.otus.homework.domain.Genre;

import java.util.List;

public interface GenreDao {
    Genre insert(Genre genre);

    void update(Genre genre);

    Genre getById(long id);

    List<Genre> getAll();

    void deleteById(long id);
}
