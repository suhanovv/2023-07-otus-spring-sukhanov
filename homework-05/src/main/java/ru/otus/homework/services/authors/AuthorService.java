package ru.otus.homework.services.authors;

import ru.otus.homework.domain.Author;
import ru.otus.homework.services.authors.dto.CreateAuthorDto;
import ru.otus.homework.services.authors.dto.UpdateAuthorDto;
import ru.otus.homework.services.authors.exceptions.AuthorAlreadyUsedException;
import ru.otus.homework.services.authors.exceptions.AuthorNotFoundException;

import java.util.List;

public interface AuthorService {
    Author create(CreateAuthorDto author);

    Author get(long id) throws AuthorNotFoundException;

    List<Author> getAll();

    Author modify(UpdateAuthorDto author) throws AuthorNotFoundException;

    void remove(long id) throws AuthorAlreadyUsedException;
}
