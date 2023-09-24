package ru.otus.homework.repositories;

import org.springframework.data.repository.ListCrudRepository;
import ru.otus.homework.models.Author;

public interface AuthorRepository extends ListCrudRepository<Author, Long> {
}
