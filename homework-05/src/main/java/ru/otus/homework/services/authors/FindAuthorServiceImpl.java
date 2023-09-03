package ru.otus.homework.services.authors;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.AuthorDao;
import ru.otus.homework.domain.Author;
import ru.otus.homework.services.authors.exceptions.AuthorNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAuthorServiceImpl implements FindAuthorService {

    private final AuthorDao dao;

    @Override
    public Author get(long id) throws AuthorNotFoundException {
        try {
            return dao.getById(id);
        } catch (EmptyResultDataAccessException e) {
            throw  new AuthorNotFoundException("Author with id: " + id + " not found");
        }
    }

    @Override
    public List<Author> getAll() {
        return dao.getAll();
    }
}
