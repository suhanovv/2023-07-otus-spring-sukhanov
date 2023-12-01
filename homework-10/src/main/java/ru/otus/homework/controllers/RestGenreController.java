package ru.otus.homework.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.services.genres.GenreService;
import ru.otus.homework.services.genres.dto.GenreDto;
import ru.otus.homework.services.genres.dto.UpdateGenreDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestGenreController {

    private final GenreService genreService;

    @GetMapping("/api/genre")
    public List<GenreDto> list() {
        return genreService.getAll();
    }

    @GetMapping("/api/genre/{genreId}")
    public GenreDto get(@PathVariable("genreId") long id) {
        return  genreService.get(id);
    }

    @PutMapping("/api/genre/{genreId}")
    public GenreDto update(@Valid  @PathVariable("genreId") long id, @RequestBody UpdateGenreDto genre) {
        return genreService.modify(id, genre);
    }
}