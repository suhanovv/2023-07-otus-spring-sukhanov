package ru.otus.homework.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@Import(BookDaoJdbc.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BookDaoJdbcTest {

    @Autowired
    private BookDaoJdbc dao;

    @Test
    void insertShouldCreateNewBook() {
        var author = new Author(1, "Роберт", "Мартин");
        var genre = new Genre(1, "Техническая литература");
        var newBook = new Book("Чистый Agile", 2000, author, genre);

        var expectedBook = new Book(2, "Чистый Agile", 2000, author, genre);

        var actualBook = dao.insert(newBook);

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void updateShouldUpdateBook() {
        var expectedBook = new Book(1, "Чистый код", 2019,
                new Author(1, "Роберт", "Мартин"),
                new Genre(1, "Техническая литература"));
        var oldBook = dao.getById(1);
        assertThat(oldBook).usingRecursiveComparison().isEqualTo(expectedBook);

        expectedBook.setTitle("Сказка про чистый код");
        expectedBook.setYear(2023);
        expectedBook.setAuthor(new Author(2, "Сказочник", "Петров"));
        expectedBook.setGenre(new Genre(2, "Сказки про код"));

        dao.update(expectedBook);

        var actualBook = dao.getById(1);

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);

    }

    @Test
    void getByIdShouldReturnExpectedBook() {
        var expectedBook = new Book(1, "Чистый код", 2019,
                new Author(1, "Роберт", "Мартин"),
                new Genre(1, "Техническая литература"));

        var actualBook = dao.getById(1);

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void getAllShouldReturnExpectedBookList() {
        var expectedBook = new Book(1, "Чистый код", 2019,
                new Author(1, "Роберт", "Мартин"),
                new Genre(1, "Техническая литература"));

        var booksList = dao.getAll();

        assertThat(booksList).containsExactlyInAnyOrder(expectedBook);
    }

    @Test
    void deleteByIdShouldDeleteCreatedAuthor() {
        var newBook = dao.insert(new Book("Чистый Agile", 2019,
                new Author(1, "Роберт", "Мартин"),
                new Genre(1, "Техническая литература")));

        dao.deleteById(newBook.getId());

        assertThatThrownBy(
                () -> dao.getById(newBook.getId()))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void deleteByIdNotFoundBookThrowException() {

        assertThatThrownBy(
                () -> dao.deleteById(300))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}