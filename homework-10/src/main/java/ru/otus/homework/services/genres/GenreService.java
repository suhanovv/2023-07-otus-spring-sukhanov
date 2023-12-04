package ru.otus.homework.services.genres;

import ru.otus.homework.services.genres.dto.CreateGenreDto;
import ru.otus.homework.services.genres.dto.GenreDto;
import ru.otus.homework.services.genres.dto.UpdateGenreDto;
import ru.otus.homework.services.genres.exceptions.GenreAlreadyUsedException;
import ru.otus.homework.services.genres.exceptions.GenreNotFoundException;

import java.util.List;

public interface GenreService {

    GenreDto create(CreateGenreDto genre);

    GenreDto get(long id) throws GenreNotFoundException;

    List<GenreDto> getAll();

    GenreDto modify(long genreId, UpdateGenreDto genre) throws GenreNotFoundException;

    void remove(long id) throws GenreAlreadyUsedException;

}
