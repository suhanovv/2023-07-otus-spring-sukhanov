package ru.otus.homework.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import ru.otus.homework.services.books.BookService;


@RestController
@RequiredArgsConstructor
public class RestBookController {

    private final BookService bookService;

    @GetMapping("/api/book")
    public Flux<BookDto> list() {
        return bookService.getAll();
    }

    @GetMapping("/api/book/{bookId}")
    public Mono<BookDto> get(@PathVariable("bookId") String id) {
        return bookService.get(id);
    }

    @PutMapping("/api/book/{bookId}")
    public Mono<BookDto> update(@Valid @PathVariable("bookId") String id, @RequestBody UpdateBookDto book) {
        return bookService.modify(id, book);
    }

    @DeleteMapping("/api/book/{bookId}")
    public Mono<Void> remove(@PathVariable("bookId") String id) {
        return bookService.remove(id);
    }

    @PostMapping("/api/book")
    public Mono<BookDto> create(@Valid @RequestBody CreateBookDto book) {
        return bookService.create(book);
    }

}