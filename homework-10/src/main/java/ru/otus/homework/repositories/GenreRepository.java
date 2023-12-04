package ru.otus.homework.repositories;

import org.springframework.data.repository.ListCrudRepository;
import ru.otus.homework.models.Genre;

public interface GenreRepository extends ListCrudRepository<Genre, Long> {
}
