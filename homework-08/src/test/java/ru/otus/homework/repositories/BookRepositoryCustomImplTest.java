package ru.otus.homework.repositories;

import lombok.val;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ComponentScan({"ru.otus.homework.repositories"})
class BookRepositoryCustomImplTest {

    @Autowired
    private BookRepositoryCustomImpl bookRepository;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Test
    void updateBooksAuthorByAuthorId() {
        val author1 = mongoTemplate.save(new Author("Иван", "Петров"));
        val author2 = mongoTemplate.save(new Author("Семен", "Семенов"));
        val newAuthor = mongoTemplate.save(new Author("Олег", "Петров"));
        val genre = new Genre("1", "Триллер");
        mongoTemplate.insertAll(List.of(
                new Book("Книга 1", 2000, author2, genre),
                new Book("Книга 2", 2000, author1, genre),
                new Book("Книга 3", 2000, author1, genre)
        ));

        bookRepository.updateBooksAuthorByAuthorId(author1.getId(), newAuthor);

        val booksWithOldAuthor = mongoTemplate.find(
                Query.query(Criteria.where("author._id").is(new ObjectId(author1.getId()))),
                Book.class
        );
        val booksWithNewAuthor = mongoTemplate.find(
                Query.query(Criteria.where("author._id").is(new ObjectId(newAuthor.getId()))),
                Book.class
        );
        assertThat(booksWithOldAuthor).isEqualTo(List.of());
        assertThat(booksWithNewAuthor).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id").containsExactlyInAnyOrder(
                new Book("Книга 2", 2000, newAuthor, genre),
                new Book("Книга 3", 2000, newAuthor, genre)
        );
    }

    @Test
    void updateBooksGenreByGenreId() {
        val author = mongoTemplate.save(new Author("Иван", "Петров"));
        val genre1 = mongoTemplate.save(new Genre("Триллер"));
        val genre2 = mongoTemplate.save(new Genre("Фантастика"));
        val newGenre = mongoTemplate.save(new Genre("Научпоп"));
        mongoTemplate.insertAll(List.of(
                new Book("Книга 1", 2000, author, genre1),
                new Book("Книга 2", 2000, author, genre1),
                new Book("Книга 3", 2000, author, genre2)
        ));

        bookRepository.updateBooksGenreByGenreId(genre1.getId(), newGenre);

        val booksWithOldGenre = mongoTemplate.find(
                Query.query(Criteria.where("genre._id").is(new ObjectId(genre1.getId()))),
                Book.class
        );
        val booksWithNewGenre = mongoTemplate.find(
                Query.query(Criteria.where("genre._id").is(new ObjectId(newGenre.getId()))),
                Book.class
        );
        assertThat(booksWithOldGenre).isEqualTo(List.of());
        assertThat(booksWithNewGenre).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id").containsExactlyInAnyOrder(
                new Book("Книга 1", 2000, author, newGenre),
                new Book("Книга 2", 2000, author, newGenre)
        );
    }
}