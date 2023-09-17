package ru.otus.homework.repositories;

import ru.otus.homework.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book insert(Book book);

    Book update(Book book);

    Optional<Book> getById(long id);

    List<Book> getAll();

    void delete(Book book);
}
