package ru.otus.homework.services.books;

import ru.otus.homework.models.Book;
import ru.otus.homework.services.books.dto.CreateBookDto;
import ru.otus.homework.services.books.dto.UpdateBookDto;
import ru.otus.homework.services.books.exceptions.BookAlreadyUsedException;
import ru.otus.homework.services.books.exceptions.BookNotFoundException;
import ru.otus.homework.services.books.exceptions.CreateBookException;
import ru.otus.homework.services.books.exceptions.ModifyBookException;

import java.util.List;

public interface BookService {

    Book create(CreateBookDto input) throws  CreateBookException;

    Book get(String id) throws BookNotFoundException;

    List<Book> getAll();

    Book modify(UpdateBookDto input) throws BookNotFoundException, ModifyBookException;

    void remove(String id) throws BookNotFoundException, BookAlreadyUsedException;
}
