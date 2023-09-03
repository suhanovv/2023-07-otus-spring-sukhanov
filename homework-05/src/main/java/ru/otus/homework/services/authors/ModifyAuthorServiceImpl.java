package ru.otus.homework.services.authors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.AuthorDao;
import ru.otus.homework.domain.Author;
import ru.otus.homework.services.authors.dto.UpdateAuthorDto;
import ru.otus.homework.services.authors.exceptions.AuthorNotFoundException;

@Service
@RequiredArgsConstructor
public class ModifyAuthorServiceImpl implements ModifyAuthorService {

    private final AuthorDao dao;

    private final FindAuthorService findService;

    @Override
    public Author modifyFromInput(UpdateAuthorDto input) throws AuthorNotFoundException {
        var author = findService.get(input.getId());

        if (input.getFirstName() != null) {
            author.setFirstName(input.getFirstName());
        }

        if (input.getLastName() != null) {
            author.setLastName(input.getLastName());
        }
        dao.update(author);

        return author;
    }
}
