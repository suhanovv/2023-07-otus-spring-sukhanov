package ru.otus.homework.repositories;

import ru.otus.homework.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    Author insert(Author author);

    Author update(Author author);

    Optional<Author> getById(long id);

    List<Author> getAll();

    void delete(Author author
    );
}
