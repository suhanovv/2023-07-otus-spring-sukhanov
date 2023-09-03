package ru.otus.homework.services.genres;

import ru.otus.homework.domain.Genre;
import ru.otus.homework.services.genres.dto.UpdateGenreDto;
import ru.otus.homework.services.genres.exceptions.GenreNotFoundException;

public interface ModifyGenreService {
    Genre modifyFromInput(UpdateGenreDto input) throws GenreNotFoundException;
}
