package ru.otus.homework.services.books;

import ru.otus.homework.domain.Book;
import ru.otus.homework.services.books.exceptions.BookNotFoundException;

import java.util.List;

public interface FindBookService {
    Book get(long id) throws BookNotFoundException;

    List<Book> getAll();
}
