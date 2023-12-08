package ru.otus.homework.services.genres;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.dto.genre.GenreDto;
import ru.otus.homework.dto.genre.UpdateGenreDto;
import ru.otus.homework.services.genres.exceptions.GenreNotFoundException;

public interface GenreService {

    Mono<GenreDto> get(String id) throws GenreNotFoundException;

    Flux<GenreDto> getAll();

    Mono<GenreDto> modify(String genreId, UpdateGenreDto genre) throws GenreNotFoundException;

}
