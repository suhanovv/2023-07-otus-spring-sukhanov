package ru.otus.homework.services.books;

import ru.otus.homework.services.books.exceptions.BookNotFoundException;

public interface RemoveBookService {
    void remove(long id) throws BookNotFoundException;
}
