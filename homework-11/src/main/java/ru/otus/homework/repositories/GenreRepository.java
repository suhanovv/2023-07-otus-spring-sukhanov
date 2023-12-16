package ru.otus.homework.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.homework.models.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
}
