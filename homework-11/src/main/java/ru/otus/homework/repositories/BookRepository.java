package ru.otus.homework.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;



public interface BookRepository extends ReactiveMongoRepository<Book, String>, BookRepositoryCustom {
    Flux<Book> findBooksByAuthor(Author author);

    Flux<Book> findBooksByGenre(Genre genre);

}

