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
import ru.otus.homework.services.books.BookService;
import ru.otus.homework.services.books.dto.BookDto;
import ru.otus.homework.services.books.dto.CreateBookDto;
import ru.otus.homework.services.books.dto.UpdateBookDto;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class RestBookController {

    private final BookService bookService;

    @GetMapping("/api/book")
    public List<BookDto> list() {
        return bookService.getAll();
    }

    @GetMapping("/api/book/{bookId}")
    public BookDto get(@PathVariable("bookId") long id) {
        return  bookService.get(id);
    }

    @PutMapping("/api/book/{bookId}")
    public BookDto update(@Valid @PathVariable("bookId") long id, @RequestBody UpdateBookDto book) {
        return bookService.modify(id, book);
    }

    @DeleteMapping("/api/book/{bookId}")
    public void remove(@PathVariable("bookId") long id) {
        bookService.remove(id);
    }

    @PostMapping("/api/book")
    public BookDto create(@Valid @RequestBody CreateBookDto book) {
        return bookService.create(book);
    }

}