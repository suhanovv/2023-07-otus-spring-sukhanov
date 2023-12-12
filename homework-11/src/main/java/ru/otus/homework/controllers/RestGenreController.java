package ru.otus.homework.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.dto.genre.GenreDto;
import ru.otus.homework.dto.genre.UpdateGenreDto;
import ru.otus.homework.repositories.BookRepository;
import ru.otus.homework.repositories.GenreRepository;
import ru.otus.homework.controllers.exceptions.GenreNotFoundException;

@RestController
@RequiredArgsConstructor
public class RestGenreController {

    private final GenreRepository repository;

    private final BookRepository bookRepository;

    @GetMapping("/api/genre")
    public Flux<GenreDto> list() {
        return repository.findAll().map(GenreDto::toDto);
    }

    @GetMapping("/api/genre/{genreId}")
    public Mono<GenreDto> get(@PathVariable("genreId") String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new GenreNotFoundException()))
                .map(GenreDto::toDto);
    }

    @PutMapping("/api/genre/{genreId}")
    public Mono<GenreDto> update(@Valid  @PathVariable("genreId") String id, @RequestBody UpdateGenreDto genre) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new GenreNotFoundException()))
                .flatMap(oldGenre -> {
                    oldGenre.setName(genre.getName());
                    return repository.save(oldGenre);
                })
                .flatMap(savedGenre -> bookRepository
                        .updateBooksGenreByGenreId(savedGenre.getId(), savedGenre)
                        .thenReturn(GenreDto.toDto(savedGenre)));
    }
}