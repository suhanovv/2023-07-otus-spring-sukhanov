package ru.otus.homework.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;
import ru.otus.homework.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends ListCrudRepository<Book, Long> {

    @Override
    @EntityGraph("book-author-genre-entity-graph")
    List<Book> findAll();

    @Override
    @EntityGraph("book-author-genre-entity-graph")
    Optional<Book> findById(Long aLong);

}

