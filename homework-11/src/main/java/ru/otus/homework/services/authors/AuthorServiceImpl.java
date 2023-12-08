package ru.otus.homework.services.authors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.repositories.AuthorRepository;
import ru.otus.homework.models.Author;
import ru.otus.homework.dto.author.AuthorDto;
import ru.otus.homework.dto.author.UpdateAuthorDto;
import ru.otus.homework.repositories.BookRepository;
import ru.otus.homework.services.authors.exceptions.AuthorNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    @Override
    public Mono<AuthorDto> get(String id) {
        return getDomainAuthor(id)
                .switchIfEmpty(Mono.error(new AuthorNotFoundException()))
                .map(AuthorDto::toDto);

    }

    @Transactional(readOnly = true)
    @Override
    public Flux<AuthorDto> getAll() {
        return repository.findAll().map(AuthorDto::toDto);
    }

    @Transactional
    @Override
    public Mono<AuthorDto> modify(String authorId, UpdateAuthorDto author) {
        return getDomainAuthor(authorId)
                .switchIfEmpty(Mono.error(new AuthorNotFoundException()))
                .flatMap(oldAuthor -> {
                    if (author.getFirstName() != null) {
                        oldAuthor.setFirstName(author.getFirstName());
                    }

                    if (author.getLastName() != null) {
                        oldAuthor.setLastName(author.getLastName());
                    }

                    return repository.save(oldAuthor).flatMap(savedAuthor -> bookRepository
                            .updateBooksAuthorByAuthorId(savedAuthor.getId(), savedAuthor).thenReturn(savedAuthor));
                })
                .map(AuthorDto::toDto);
    }

    private Mono<Author> getDomainAuthor(String id) {
        return repository.findById(id);
    }
}
