package ru.otus.homework.repositories;

import reactor.core.publisher.Mono;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Genre;


public interface BookRepositoryCustom {
    Mono<Void> updateBooksAuthorByAuthorId(String authorId, Author author);

    Mono<Void> updateBooksGenreByGenreId(String genreId, Genre genre);
}

