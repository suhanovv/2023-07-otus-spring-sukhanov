package ru.otus.homework.services.books;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.dto.book.BookDto;
import ru.otus.homework.dto.book.CreateBookDto;
import ru.otus.homework.dto.book.UpdateBookDto;
import ru.otus.homework.services.books.exceptions.BookNotFoundException;
import ru.otus.homework.services.books.exceptions.CreateBookException;
import ru.otus.homework.services.books.exceptions.ModifyBookException;

public interface BookService {

    Mono<BookDto> create(CreateBookDto input) throws  CreateBookException;

    Mono<BookDto> get(String id) throws BookNotFoundException;

    Flux<BookDto> getAll();

    Mono<BookDto> modify(String bookId, UpdateBookDto input) throws BookNotFoundException, ModifyBookException;

    Mono<Void> remove(String id);
}
