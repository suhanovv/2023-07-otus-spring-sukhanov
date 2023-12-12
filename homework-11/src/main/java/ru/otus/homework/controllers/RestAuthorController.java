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
import ru.otus.homework.repositories.AuthorRepository;
import ru.otus.homework.repositories.BookRepository;
import ru.otus.homework.controllers.exceptions.AuthorNotFoundException;


@RestController
@RequiredArgsConstructor
public class RestAuthorController {


    private final AuthorRepository repository;

    private final BookRepository bookRepository;

    @GetMapping("/api/author")
    public Flux<AuthorDto> list() {
        return repository.findAll().map(AuthorDto::toDto);
    }

    @GetMapping("/api/author/{authorId}")
    public Mono<AuthorDto> get(@PathVariable("authorId") String id) {
        return  repository.findById(id)
                .switchIfEmpty(Mono.error(new AuthorNotFoundException()))
                .map(AuthorDto::toDto);
    }

    @PutMapping("/api/author/{authorId}")
    public Mono<AuthorDto> update(@PathVariable("authorId") String id, @RequestBody UpdateAuthorDto author) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new AuthorNotFoundException()))
                .flatMap(oldAuthor -> {
                    oldAuthor.setFirstName(author.getFirstName());
                    oldAuthor.setLastName(author.getLastName());
                    return repository.save(oldAuthor);
                })
                .flatMap(savedAuthor -> bookRepository
                        .updateBooksAuthorByAuthorId(savedAuthor.getId(), savedAuthor)
                        .thenReturn(AuthorDto.toDto(savedAuthor))
                );
    }

}