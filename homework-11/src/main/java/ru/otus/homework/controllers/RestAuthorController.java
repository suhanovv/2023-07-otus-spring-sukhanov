package ru.otus.homework.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.dto.author.AuthorDto;
import ru.otus.homework.dto.author.UpdateAuthorDto;
import ru.otus.homework.services.authors.AuthorService;


@RestController
@RequiredArgsConstructor
public class RestAuthorController {

    private final AuthorService authorService;

    @GetMapping("/api/author")
    public Flux<AuthorDto> list() {
        return authorService.getAll();
    }

    @GetMapping("/api/author/{authorId}")
    public Mono<AuthorDto> get(@PathVariable("authorId") String id) {
        return  authorService.get(id);
    }

    @PutMapping("/api/author/{authorId}")
    public Mono<AuthorDto> update(@PathVariable("authorId") String id, @RequestBody UpdateAuthorDto author) {
        return authorService.modify(id, author);
    }

}