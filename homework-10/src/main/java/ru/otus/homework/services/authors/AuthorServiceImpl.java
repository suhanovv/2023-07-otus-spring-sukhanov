package ru.otus.homework.services.authors;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.repositories.AuthorRepository;
import ru.otus.homework.models.Author;
import ru.otus.homework.services.authors.dto.AuthorDto;
import ru.otus.homework.services.authors.dto.CreateAuthorDto;
import ru.otus.homework.services.authors.dto.UpdateAuthorDto;
import ru.otus.homework.services.authors.exceptions.AuthorAlreadyUsedException;
import ru.otus.homework.services.authors.exceptions.AuthorNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    @Transactional
    @Override
    public AuthorDto create(CreateAuthorDto author) {
        var newAuthor = new Author(0, author.getFirstName(), author.getLastName());
        return AuthorDto.toDto(repository.save(newAuthor));
    }

    @Transactional(readOnly = true)
    @Override
    public AuthorDto get(long id) {
        return AuthorDto.toDto(getDomainAuthor(id));

    }

    @Transactional(readOnly = true)
    @Override
    public List<AuthorDto> getAll() {
        return repository.findAll().stream().map(AuthorDto::toDto).toList();
    }

    @Transactional
    @Override
    public AuthorDto modify(long authorId, UpdateAuthorDto author) {
        var oldAuthor = getDomainAuthor(authorId);

        if (author.getFirstName() != null) {
            oldAuthor.setFirstName(author.getFirstName());
        }

        if (author.getLastName() != null) {
            oldAuthor.setLastName(author.getLastName());
        }
        repository.save(oldAuthor);

        return AuthorDto.toDto(oldAuthor);
    }

    @Transactional
    @Override
    public void remove(long id) {
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new AuthorAlreadyUsedException("Author id: " + id + " can't be delete");
        }
    }

    private Author getDomainAuthor(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author with id: " + id + " not found"));
    }
}
