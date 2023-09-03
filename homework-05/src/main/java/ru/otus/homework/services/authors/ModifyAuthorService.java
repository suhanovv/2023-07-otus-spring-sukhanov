package ru.otus.homework.services.authors;

import ru.otus.homework.domain.Author;
import ru.otus.homework.services.authors.dto.UpdateAuthorDto;
import ru.otus.homework.services.authors.exceptions.AuthorNotFoundException;

public interface ModifyAuthorService {
    Author modifyFromInput(UpdateAuthorDto authorInput) throws AuthorNotFoundException;
}
