package ru.otus.homework.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;

import java.util.List;


public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findBooksByAuthor(Author author);

    List<Book> findBooksByGenre(Genre genre);

    long countBookByAuthor(Author author);

    long countBookByGenre(Genre genre);

}

