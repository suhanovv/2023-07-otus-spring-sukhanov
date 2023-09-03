package ru.otus.homework.services.genres;

import ru.otus.homework.services.genres.exceptions.GenreAlreadyUsedException;
import ru.otus.homework.services.genres.exceptions.GenreNotFoundException;

public interface RemoveGenreService {
    void remove(long id) throws GenreAlreadyUsedException, GenreNotFoundException;
}
