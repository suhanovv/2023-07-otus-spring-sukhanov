package ru.otus.homework.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.services.authors.AuthorService;
import ru.otus.homework.services.authors.dto.AuthorDto;
import ru.otus.homework.services.authors.dto.UpdateAuthorDto;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class RestAuthorController {

    private final AuthorService authorService;

    @GetMapping("/api/author")
    public List<AuthorDto> list() {
        return authorService.getAll();
    }

    @GetMapping("/api/author/{authorId}")
    public AuthorDto get(@PathVariable("authorId") long id) {
        return  authorService.get(id);
    }

    @PutMapping("/api/author/{authorId}")
    public AuthorDto update(@PathVariable("authorId") long id, @RequestBody UpdateAuthorDto author) {
        return authorService.modify(id, author);
    }

}