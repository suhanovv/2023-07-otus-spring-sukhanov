package ru.otus.homework.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.homework.models.Book;



public interface BookRepository extends ReactiveMongoRepository<Book, String>, BookRepositoryCustom {

}

