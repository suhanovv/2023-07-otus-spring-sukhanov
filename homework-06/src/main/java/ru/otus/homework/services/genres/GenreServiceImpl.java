package ru.otus.homework.services.genres;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.repositories.GenreRepository;
import ru.otus.homework.models.Genre;
import ru.otus.homework.services.genres.dto.CreateGenreDto;
import ru.otus.homework.services.genres.dto.UpdateGenreDto;
import ru.otus.homework.services.genres.exceptions.GenreAlreadyUsedException;
import ru.otus.homework.services.genres.exceptions.GenreNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository repository;

    @Transactional
    @Override
    public Genre create(CreateGenreDto genre) {
        var newGenre = new Genre(0, genre.getName());
        return repository.insert(newGenre);
    }

    @Transactional(readOnly = true)
    @Override
    public Genre get(long id) throws GenreNotFoundException {
        return repository.getById(id)
                .orElseThrow(() -> new GenreNotFoundException("Genre with id: " + id + " not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> getAll() {
        return repository.getAll();
    }

    @Transactional
    @Override
    public Genre modify(UpdateGenreDto genre) throws GenreNotFoundException {
        var oldGenre = get(genre.getId());

        if (genre.getName() != null) {
            oldGenre.setName(genre.getName());
        }
        repository.update(oldGenre);

        return oldGenre;
    }

    @Transactional
    @Override
    public void remove(long id) throws GenreAlreadyUsedException, GenreNotFoundException {
        var genre = get(id);
        try {
            repository.delete(genre);
        } catch (DataIntegrityViolationException e) {
            throw new GenreAlreadyUsedException("Genre id: " + id + " can't be delete");
        }
    }
}
