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
import ru.otus.homework.services.genres.GenreService;

@RestController
@RequiredArgsConstructor
public class RestGenreController {

    private final GenreService service;

    @GetMapping("/api/genre")
    public Flux<GenreDto> list() {
        return service.getAll();
    }

    @GetMapping("/api/genre/{genreId}")
    public Mono<GenreDto> get(@PathVariable("genreId") String id) {
        return service.get(id);
    }

    @PutMapping("/api/genre/{genreId}")
    public Mono<GenreDto> update(@Valid  @PathVariable("genreId") String id, @RequestBody UpdateGenreDto genre) {
        return service.modify(id, genre);
    }
}