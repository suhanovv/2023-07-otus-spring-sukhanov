package ru.otus.homework.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.dto.book.BookDto;
import ru.otus.homework.dto.book.CreateBookDto;
import ru.otus.homework.dto.book.UpdateBookDto;
import ru.otus.homework.models.Book;
import ru.otus.homework.repositories.AuthorRepository;
import ru.otus.homework.repositories.BookRepository;
import ru.otus.homework.repositories.GenreRepository;
import ru.otus.homework.controllers.exceptions.BookNotFoundException;


@RestController
@RequiredArgsConstructor
public class RestBookController {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    @GetMapping("/api/book")
    public Flux<BookDto> list() {
        return bookRepository.findAll().map(BookDto::toDto);
    }

    @GetMapping("/api/book/{bookId}")
    public Mono<BookDto> get(@PathVariable("bookId") String id) {
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.error(new BookNotFoundException()))
                .map(BookDto::toDto);
    }

    @PutMapping("/api/book/{bookId}")
    public Mono<BookDto> update(@Valid @PathVariable("bookId") String id, @RequestBody UpdateBookDto book) {
        return Mono.zip(
                bookRepository.findById(id),
                authorRepository.findById(book.getAuthorId()),
                genreRepository.findById(book.getGenreId())
        ).flatMap((result) -> {
            val bookForUpdate = result.getT1();
            val author = result.getT2();
            val genre = result.getT3();

            bookForUpdate.setTitle(book.getTitle());
            bookForUpdate.setPublishYear(book.getYear());
            bookForUpdate.setAuthor(author);
            bookForUpdate.setGenre(genre);
            return bookRepository.save(bookForUpdate);
        }).map(BookDto::toDto);
    }

    @DeleteMapping("/api/book/{bookId}")
    public Mono<Void> remove(@PathVariable("bookId") String id) {
        return bookRepository.deleteById(id);
    }

    @PostMapping("/api/book")
    public Mono<BookDto> create(@Valid @RequestBody CreateBookDto book) {
        var genreMono = genreRepository.findById(book.getGenreId());
        var authorMono = authorRepository.findById(book.getAuthorId());

        return Mono.zip(genreMono, authorMono).flatMap(response -> {
            val genre = response.getT1();
            val author = response.getT2();
            var newBook = new Book(
                    book.getTitle(), book.getYear(), author, genre);
            return bookRepository.save(newBook);
        }).map(BookDto::toDto);
    }

}