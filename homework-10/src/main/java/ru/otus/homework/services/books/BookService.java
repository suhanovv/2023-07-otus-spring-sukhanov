package ru.otus.homework.services.books;

import ru.otus.homework.services.books.dto.BookDto;
import ru.otus.homework.services.books.dto.CreateBookDto;
import ru.otus.homework.services.books.dto.UpdateBookDto;
import ru.otus.homework.services.books.exceptions.BookNotFoundException;
import ru.otus.homework.services.books.exceptions.CreateBookException;
import ru.otus.homework.services.books.exceptions.ModifyBookException;

import java.util.List;

public interface BookService {

    BookDto create(CreateBookDto input) throws  CreateBookException;

    BookDto get(long id) throws BookNotFoundException;

    List<BookDto> getAll();

    BookDto modify(long bookId, UpdateBookDto input) throws BookNotFoundException, ModifyBookException;

    void remove(long id);
}
