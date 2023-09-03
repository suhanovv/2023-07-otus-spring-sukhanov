package ru.otus.homework.services.genres;

import ru.otus.homework.domain.Genre;
import ru.otus.homework.services.genres.dto.CreateGenreDto;

public interface CreateGenreService {
    Genre createFromInput(CreateGenreDto input);
}
