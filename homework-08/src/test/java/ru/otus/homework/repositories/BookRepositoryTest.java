package ru.otus.homework.repositories;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void findBooksByAuthorShouldReturnExpectedBooksCount() {
        val author1 = mongoTemplate.save(new Author("Не скучный", "Автор"));
        val author2 = mongoTemplate.save(new Author("Cкучный", "Автор"));
        val genre = mongoTemplate.save(new Genre("Проза"));
        val books = bookRepository.saveAll(List.of(
                new Book("Книга 1", 2000, author1, genre),
                new Book("Книга 2", 2000, author1, genre),
                new Book("Книга 3", 2000, author1, genre),
                new Book("Книга 4", 2000, author1, genre),
                new Book("Книга 5", 2000, author2, genre),
                new Book("Книга 6", 2000, author2, genre)
        ));
        val expected1 = Stream.of(0, 1, 2, 3).map(books::get).toList();
        val expected2 = Stream.of(4, 5).map(books::get).toList();

        val author1books = bookRepository.findBooksByAuthor(author1);
        val author2books = bookRepository.findBooksByAuthor(author2);

        assertThat(author1books).containsExactlyInAnyOrderElementsOf(expected1);
        assertThat(author2books).containsExactlyInAnyOrderElementsOf(expected2);

    }

    @Test
    void findBooksByGenreShouldReturnExpectedBooksCount() {
        val author = mongoTemplate.save(new Author("Не скучный", "Автор"));
        val genre1 = mongoTemplate.save(new Genre("Проза"));
        val genre2 = mongoTemplate.save(new Genre("Детектив"));
        val books = bookRepository.saveAll(List.of(
                new Book("Книга 1", 2000, author, genre1),
                new Book("Книга 2", 2000, author, genre2),
                new Book("Книга 3", 2000, author, genre2),
                new Book("Книга 4", 2000, author, genre1),
                new Book("Книга 5", 2000, author, genre2),
                new Book("Книга 6", 2000, author, genre1)
        ));
        val expected1 = Stream.of(0, 3, 5).map(books::get).toList();
        val expected2 = Stream.of(1, 2, 4).map(books::get).toList();

        val genre1books = bookRepository.findBooksByGenre(genre1);
        val genre2books = bookRepository.findBooksByGenre(genre2);

        assertThat(genre1books).containsExactlyInAnyOrderElementsOf(expected1);
        assertThat(genre2books).containsExactlyInAnyOrderElementsOf(expected2);
    }

    @Test
    void countBookByAuthorShouldReturnExpectedBooksCount() {
        val author1 = mongoTemplate.save(new Author("Не скучный", "Автор"));
        val author2 = mongoTemplate.save(new Author("Cкучный", "Автор"));
        val genre = mongoTemplate.save(new Genre("Проза"));
        val books = List.of(
                new Book("Книга 1", 2000, author1, genre),
                new Book("Книга 2", 2000, author1, genre),
                new Book("Книга 3", 2000, author1, genre),
                new Book("Книга 4", 2000, author1, genre),
                new Book("Книга 5", 2000, author2, genre),
                new Book("Книга 6", 2000, author2, genre)
        );
        bookRepository.saveAll(books);

        val author1booksCount = bookRepository.countBookByAuthor(author1);
        val author2booksCount = bookRepository.countBookByAuthor(author2);

        assertThat(author1booksCount).isEqualTo(4);
        assertThat(author2booksCount).isEqualTo(2);
    }

    @Test
    void countBookByGenreShouldReturnExpectedBooksCount() {
        val author = mongoTemplate.save(new Author("Не скучный", "Автор"));
        val genre1 = mongoTemplate.save(new Genre("Проза"));
        val genre2 = mongoTemplate.save(new Genre("Детектив"));
        val books = List.of(
                new Book("Книга 1", 2000, author, genre1),
                new Book("Книга 2", 2000, author, genre2),
                new Book("Книга 3", 2000, author, genre2),
                new Book("Книга 4", 2000, author, genre1),
                new Book("Книга 5", 2000, author, genre2),
                new Book("Книга 6", 2000, author, genre1)
        );
        bookRepository.saveAll(books);

        val genre1booksCount = bookRepository.countBookByGenre(genre1);
        val genre2booksCount = bookRepository.countBookByGenre(genre2);

        assertThat(genre1booksCount).isEqualTo(3);
        assertThat(genre2booksCount).isEqualTo(3);
    }
}