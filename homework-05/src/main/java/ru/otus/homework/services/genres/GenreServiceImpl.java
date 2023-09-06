package ru.otus.homework.services.genres;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import ru.otus.homework.dao.GenreDao;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.services.genres.dto.CreateGenreDto;
import ru.otus.homework.services.genres.dto.UpdateGenreDto;
import ru.otus.homework.services.genres.exceptions.GenreAlreadyUsedException;
import ru.otus.homework.services.genres.exceptions.GenreNotFoundException;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreDao dao;

    @Override
    public Genre create(CreateGenreDto genre) {
        var newGenre = new Genre(genre.getName());
        return dao.insert(newGenre);
    }

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

    @Override
    public Genre modify(UpdateGenreDto genre) throws GenreNotFoundException {
        var oldGenre = get(genre.getId());

        if (genre.getName() != null) {
            oldGenre.setName(genre.getName());
        }
        dao.update(oldGenre);

        return oldGenre;
    }

    @Override
    public void remove(long id) throws GenreAlreadyUsedException {
        try {
            dao.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new GenreAlreadyUsedException("Genre id: " + id + " can't be delete");
        }
    }
}
