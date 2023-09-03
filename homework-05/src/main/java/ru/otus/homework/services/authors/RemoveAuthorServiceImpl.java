package ru.otus.homework.services.authors;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.AuthorDao;
import ru.otus.homework.services.authors.exceptions.AuthorAlreadyUsedException;
import ru.otus.homework.services.authors.exceptions.AuthorNotFoundException;

@Service
@RequiredArgsConstructor
public class RemoveAuthorServiceImpl implements RemoveAuthorService {

    private final AuthorDao dao;

    @Override
    public void remove(long id) throws AuthorAlreadyUsedException, AuthorNotFoundException {
        try {
            dao.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new AuthorAlreadyUsedException("Author id: " + id + " can't be delete");
        } catch (EmptyResultDataAccessException e) {
            throw new AuthorNotFoundException("Author with id: " + id + "doesn't exists");
        }
    }
}
