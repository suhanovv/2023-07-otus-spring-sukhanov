package ru.otus.homework.services.authors;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.dto.author.AuthorDto;
import ru.otus.homework.dto.author.UpdateAuthorDto;
import ru.otus.homework.services.authors.exceptions.AuthorNotFoundException;

public interface AuthorService {
    Mono<AuthorDto> get(String id) throws AuthorNotFoundException;

    Flux<AuthorDto> getAll();

    Mono<AuthorDto> modify(String authorId, UpdateAuthorDto author) throws AuthorNotFoundException;
}
