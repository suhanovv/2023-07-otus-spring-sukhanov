package ru.otus.homework.services.genres;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.repositories.GenreRepository;
import ru.otus.homework.models.Genre;
import ru.otus.homework.services.genres.dto.CreateGenreDto;
import ru.otus.homework.services.genres.dto.GenreDto;
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
    public GenreDto create(CreateGenreDto genre) {
        var newGenre = new Genre(0, genre.getName());
        return GenreDto.toDto(repository.save(newGenre));
    }

    @Transactional(readOnly = true)
    @Override
    public GenreDto get(long id) {
        return GenreDto.toDto(getDomainGenre(id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<GenreDto> getAll() {
        return repository.findAll().stream().map(GenreDto::toDto).toList();
    }

    @Transactional
    @Override
    public GenreDto modify(long genreId, UpdateGenreDto genre) {
        var oldGenre = getDomainGenre(genreId);

        if (genre.getName() != null) {
            oldGenre.setName(genre.getName());
        }
        repository.save(oldGenre);

        return GenreDto.toDto(oldGenre);
    }

    @Transactional
    @Override
    public void remove(long id) {
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new GenreAlreadyUsedException("Genre id: " + id + " can't be delete");
        }
    }

    private Genre getDomainGenre(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new GenreNotFoundException("Genre with id: " + id + " not found"));
    }

}
