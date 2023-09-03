package ru.otus.homework.services.authors;

import ru.otus.homework.domain.Author;
import ru.otus.homework.services.authors.dto.CreateAuthorDto;

public interface CreateAuthorService {
    Author createFromInput(CreateAuthorDto input);
}
