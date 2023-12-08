package ru.otus.homework.services.genres;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.dto.genre.GenreDto;
import ru.otus.homework.dto.genre.UpdateGenreDto;
import ru.otus.homework.models.Genre;
import ru.otus.homework.repositories.BookRepository;
import ru.otus.homework.repositories.GenreRepository;
import ru.otus.homework.services.genres.exceptions.GenreNotFoundException;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository repository;

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    @Override
    public Mono<GenreDto> get(String id) {
        return getDomainGenre(id)
                .switchIfEmpty(Mono.error(new GenreNotFoundException()))
                .map(GenreDto::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public Flux<GenreDto> getAll() {
        return repository.findAll().map(GenreDto::toDto);
    }

    @Transactional
    @Override
    public Mono<GenreDto> modify(String genreId, UpdateGenreDto genre) {
        return getDomainGenre(genreId)
                .switchIfEmpty(Mono.error(new GenreNotFoundException()))
                .flatMap(oldGenre -> {
                    if (genre.getName() != null) {
                        oldGenre.setName(genre.getName());
                    }
                    return repository.save(oldGenre)
                            .flatMap(savedGenre -> bookRepository
                                    .updateBooksGenreByGenreId(savedGenre.getId(), savedGenre).thenReturn(savedGenre));
                }).map(GenreDto::toDto);
    }

    private Mono<Genre> getDomainGenre(String id) {
        return repository.findById(id);

    }

}
