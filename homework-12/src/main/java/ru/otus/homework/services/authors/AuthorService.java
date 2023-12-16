package ru.otus.homework.services.authors;

import ru.otus.homework.services.authors.dto.AuthorDto;
import ru.otus.homework.services.authors.dto.CreateAuthorDto;
import ru.otus.homework.services.authors.exceptions.AuthorAlreadyUsedException;
import ru.otus.homework.services.authors.exceptions.AuthorNotFoundException;

import java.util.List;

public interface AuthorService {
    AuthorDto create(CreateAuthorDto author);

    AuthorDto get(long id) throws AuthorNotFoundException;

    List<AuthorDto> getAll();

    AuthorDto modify(AuthorDto author) throws AuthorNotFoundException;

    void remove(long id) throws AuthorAlreadyUsedException, AuthorNotFoundException;
}
