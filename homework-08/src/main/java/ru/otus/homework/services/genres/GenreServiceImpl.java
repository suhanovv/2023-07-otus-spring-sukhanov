package ru.otus.homework.services.genres;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.repositories.BookRepository;
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

    private final BookRepository bookRepository;

    @Transactional
    @Override
    public Genre create(CreateGenreDto genre) {
        var newGenre = new Genre(genre.getName());
        return repository.save(newGenre);
    }

    @Transactional(readOnly = true)
    @Override
    public Genre get(String id) throws GenreNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new GenreNotFoundException("Genre with id: " + id + " not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> getAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public Genre modify(UpdateGenreDto genre) throws GenreNotFoundException {
        var oldGenre = get(genre.getId());

        if (genre.getName() != null) {
            oldGenre.setName(genre.getName());
        }
        val newGenre = repository.save(oldGenre);
        bookRepository.updateBooksGenreByGenreId(oldGenre.getId(), newGenre);

        return newGenre;
    }

    @Transactional
    @Override
    public void remove(String id) throws GenreAlreadyUsedException, GenreNotFoundException {
        var genre = get(id);

        var countOfBooks = bookRepository.countBookByGenre(genre);
        if (countOfBooks > 0) {
            throw new GenreAlreadyUsedException("Genre id: " + id + " can't be delete");
        }
        repository.delete(genre);

    }

}
