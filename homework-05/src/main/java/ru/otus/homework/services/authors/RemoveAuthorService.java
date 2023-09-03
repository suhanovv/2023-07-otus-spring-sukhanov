package ru.otus.homework.services.authors;

import ru.otus.homework.services.authors.exceptions.AuthorAlreadyUsedException;
import ru.otus.homework.services.authors.exceptions.AuthorNotFoundException;

public interface RemoveAuthorService {
    void remove(long id) throws AuthorAlreadyUsedException, AuthorNotFoundException;
}
