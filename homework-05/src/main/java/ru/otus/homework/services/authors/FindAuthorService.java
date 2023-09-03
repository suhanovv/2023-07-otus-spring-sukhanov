package ru.otus.homework.services.authors;

import ru.otus.homework.domain.Author;
import ru.otus.homework.services.authors.exceptions.AuthorNotFoundException;

import java.util.List;

public interface FindAuthorService {
    Author get(long id) throws AuthorNotFoundException;

    List<Author> getAll();
}
