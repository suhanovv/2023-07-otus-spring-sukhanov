package ru.otus.homework.services.books;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.dto.author.AuthorDto;
import ru.otus.homework.dto.book.BookDto;
import ru.otus.homework.dto.book.CreateBookDto;
import ru.otus.homework.dto.book.UpdateBookDto;
import ru.otus.homework.dto.genre.GenreDto;
import ru.otus.homework.models.Book;
import ru.otus.homework.repositories.BookRepository;
import ru.otus.homework.services.authors.AuthorService;
import ru.otus.homework.services.books.exceptions.BookNotFoundException;
import ru.otus.homework.services.genres.GenreService;

import java.time.Year;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    private final AuthorService authorService;

    private final GenreService genreService;

    @Transactional
    @Override
    public Mono<BookDto> create(CreateBookDto book) {
        var genreMono = genreService.get(book.getGenreId());
        var authorMono = authorService.get(book.getAuthorId());

        return Mono.zip(genreMono, authorMono).flatMap(response -> {
            val genre = response.getT1();
            val author = response.getT2();
            var newBook = new Book(
                    book.getTitle(), book.getYear(), AuthorDto.toDomain(author), GenreDto.toDomain(genre));
            return repository.save(newBook);
        }).map(BookDto::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public Mono<BookDto> get(String id) {

        return getDomainBook(id)
                .switchIfEmpty(Mono.error(new BookNotFoundException()))
                .map(BookDto::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public Flux<BookDto> getAll() {

        return repository.findAll().map(BookDto::toDto);
    }

    @Transactional
    @Override
    public Mono<BookDto> modify(String bookId, UpdateBookDto book) {

        return getDomainBook(bookId)
                .switchIfEmpty(Mono.error(new BookNotFoundException()))
                .flatMap(oldBook -> {
                    updateTitle(oldBook, book);
                    updateYear(oldBook, book);
                    return Mono.when(
                            updateAuthor(oldBook, book),
                            updateGenre(oldBook, book)
                    ).then(repository.save(oldBook));
                }).map(BookDto::toDto);
    }


    @Transactional
    @Override
    public Mono<Void> remove(String id) {
        return repository.deleteById(id);
    }

    private void updateTitle(Book oldBook, UpdateBookDto book) {
        if (book.getTitle() != null) {
            oldBook.setTitle(book.getTitle());
        }
    }

    private void updateYear(Book oldBook, UpdateBookDto book) {
        if (!book.getYear().equals(Year.of(0))) {
            oldBook.setPublishYear(book.getYear());
        }
    }

    private Mono<Void> updateAuthor(Book oldBook, UpdateBookDto book) {
        if (!book.getAuthorId().isEmpty()) {
            return authorService.get(book.getAuthorId()).flatMap(author -> {

                oldBook.setAuthor(AuthorDto.toDomain(author));
                return Mono.empty();
            });
        }
        return Mono.empty();
    }

    private Mono<Void> updateGenre(Book oldBook, UpdateBookDto book) {
        if (!book.getGenreId().isEmpty()) {
            return genreService.get(book.getGenreId()).flatMap(genre -> {

                oldBook.setGenre(GenreDto.toDomain(genre));
                return Mono.empty();
            });
        }
        return Mono.empty();
    }

    private Mono<Book> getDomainBook(String id) {
        return repository.findById(id);
    }
}
