package ru.otus.homework.services.genres;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.GenreDao;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.services.genres.exceptions.GenreNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindGenreServiceImpl implements FindGenreService {

    private final GenreDao dao;

    @Override
    public Genre get(long id) throws GenreNotFoundException {
        try {
            return dao.getById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new GenreNotFoundException("Genre with id: " + id + " not found");
        }
    }

    @Override
    public List<Genre> getAll() {
        return dao.getAll();
    }
}
