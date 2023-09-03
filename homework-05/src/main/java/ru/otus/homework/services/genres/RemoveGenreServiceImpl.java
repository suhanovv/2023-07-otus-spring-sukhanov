package ru.otus.homework.services.genres;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.GenreDao;
import ru.otus.homework.services.genres.exceptions.GenreAlreadyUsedException;
import ru.otus.homework.services.genres.exceptions.GenreNotFoundException;

@Service
@RequiredArgsConstructor
public class RemoveGenreServiceImpl implements RemoveGenreService {

    private final GenreDao dao;

    @Override
    public void remove(long id) throws GenreAlreadyUsedException, GenreNotFoundException {
        try {
            dao.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new GenreAlreadyUsedException("Genre id: " + id + " can't be delete");
        } catch (EmptyResultDataAccessException e) {
            throw new GenreNotFoundException("Genre with id: " + id + "doesn't exists");
        }
    }
}
