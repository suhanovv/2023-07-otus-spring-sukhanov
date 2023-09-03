package ru.otus.homework.services.genres;

import ru.otus.homework.domain.Genre;
import ru.otus.homework.services.genres.exceptions.GenreNotFoundException;

import java.util.List;

public interface FindGenreService {
    Genre get(long id) throws GenreNotFoundException;

    List<Genre> getAll();
}
