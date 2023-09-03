package ru.otus.homework.services.authors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.AuthorDao;
import ru.otus.homework.domain.Author;
import ru.otus.homework.services.authors.dto.CreateAuthorDto;

@Service
@RequiredArgsConstructor
public class CreateAuthorServiceImpl implements CreateAuthorService {

    private final AuthorDao dao;

    @Override
    public Author createFromInput(CreateAuthorDto input) {
        var author = new Author(input.getFirstName(), input.getLastName());
        return dao.insert(author);
    }
}
