package ru.otus.homework.services.authors;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.AuthorDao;
import ru.otus.homework.domain.Author;
import ru.otus.homework.services.authors.dto.CreateAuthorDto;
import ru.otus.homework.services.authors.dto.UpdateAuthorDto;
import ru.otus.homework.services.authors.exceptions.AuthorAlreadyUsedException;
import ru.otus.homework.services.authors.exceptions.AuthorNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao dao;

    @Override
    public Author create(CreateAuthorDto author) {
        var newAuthor = new Author(author.getFirstName(), author.getLastName());
        return dao.insert(newAuthor);
    }

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

    @Override
    public Author modify(UpdateAuthorDto author) throws AuthorNotFoundException {
        var oldAuthor = get(author.getId());

        if (author.getFirstName() != null) {
            oldAuthor.setFirstName(author.getFirstName());
        }

        if (author.getLastName() != null) {
            oldAuthor.setLastName(author.getLastName());
        }
        dao.update(oldAuthor);

        return oldAuthor;
    }

    @Override
    public void remove(long id) throws AuthorAlreadyUsedException {
        try {
            dao.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new AuthorAlreadyUsedException("Author id: " + id + " can't be delete");
        }
    }
}
