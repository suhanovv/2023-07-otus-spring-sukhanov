package ru.otus.homework.services.genres;

import ru.otus.homework.models.Genre;
import ru.otus.homework.services.genres.dto.CreateGenreDto;
import ru.otus.homework.services.genres.dto.UpdateGenreDto;
import ru.otus.homework.services.genres.exceptions.GenreAlreadyUsedException;
import ru.otus.homework.services.genres.exceptions.GenreNotFoundException;

import java.util.List;

public interface GenreService {

    Genre create(CreateGenreDto genre);

    Genre get(String id) throws GenreNotFoundException;

    List<Genre> getAll();

    Genre modify(UpdateGenreDto genre) throws GenreNotFoundException;

    void remove(String id) throws GenreAlreadyUsedException, GenreNotFoundException;

}
