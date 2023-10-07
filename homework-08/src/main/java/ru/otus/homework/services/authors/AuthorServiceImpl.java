package ru.otus.homework.services.authors;

import lombok.RequiredArgsConstructor;

import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.repositories.AuthorRepository;
import ru.otus.homework.models.Author;
import ru.otus.homework.repositories.BookRepository;
import ru.otus.homework.services.authors.dto.CreateAuthorDto;
import ru.otus.homework.services.authors.dto.UpdateAuthorDto;
import ru.otus.homework.services.authors.exceptions.AuthorAlreadyUsedException;
import ru.otus.homework.services.authors.exceptions.AuthorNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    private final BookRepository bookRepository;

    @Transactional
    @Override
    public Author create(CreateAuthorDto author) {
        var newAuthor = new Author(author.getFirstName(), author.getLastName());
        return repository.save(newAuthor);
    }

    @Transactional(readOnly = true)
    @Override
    public Author get(String id) throws AuthorNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author with id: " + id + " not found"));

    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> getAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public Author modify(UpdateAuthorDto author) throws AuthorNotFoundException {
        var oldAuthor = get(author.getId());

        if (author.getFirstName() != null) {
            oldAuthor.setFirstName(author.getFirstName());
        }

        if (author.getLastName() != null) {
            oldAuthor.setLastName(author.getLastName());
        }
        val newAuthor = repository.save(oldAuthor);
        bookRepository.updateBooksAuthorByAuthorId(oldAuthor.getId(), newAuthor);

        return newAuthor;
    }

    @Transactional
    @Override
    public void remove(String id) throws AuthorAlreadyUsedException, AuthorNotFoundException {
        var author = get(id);
        var countOfBooks = bookRepository.countBookByAuthor(author);
        if (countOfBooks > 0) {
            throw new AuthorAlreadyUsedException("Author id: " + id + " can't be delete");
        }
        repository.delete(author);
    }
}
