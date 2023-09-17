package ru.otus.homework.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {

    @Autowired
    private BookRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @Test
    void insertShouldCreateNewBook() {
        var author = new Author(1, "Роберт", "Мартин");
        var genre = new Genre(1, "Техническая литература");
        var expectedBook = new Book(0, "Чистый Agile", 2000, author, genre);
        var query = em.getEntityManager().createQuery("select b from Book b", Book.class);
        var books = query.getResultList();

        var actualBook = repositoryJpa.insert(expectedBook);

        assertThat(books)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .doesNotContain(expectedBook);
        assertThat(actualBook).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedBook);
    }

    @Test
    void updateShouldUpdateBook() {
        var expectedBook = em.persistFlushFind(new Book(0, "Чистейший код", 2019,
                new Author(1, "Роберт", "Мартин"),
                new Genre(1, "Техническая литература")));


        expectedBook.setTitle("Сказка про чистый код");
        expectedBook.setPublishYear(2023);
        expectedBook.setAuthor(new Author(2, "Сказочник", "Петров"));
        expectedBook.setGenre(new Genre(2, "Сказки про код"));

        repositoryJpa.update(expectedBook);

        var actualBook = em.find(Book.class, expectedBook.getId());

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);

    }

    @Test
    void getByIdShouldReturnExpectedBook() {
        var expectedBook = em.find(Book.class, 1);

        var actualBook = repositoryJpa.getById(1).orElseThrow();

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void getAllShouldReturnExpectedBookList() {
        var expectedBook = new Book(1, "Чистый код", 2019,
                new Author(1, "Роберт", "Мартин"),
                new Genre(1, "Техническая литература"));

        var booksList = repositoryJpa.getAll();

        assertThat(booksList).containsExactlyInAnyOrder(expectedBook);
    }

    @Test
    void deleteByIdShouldDeleteCreatedAuthor() {
        var newBook = em.persistFlushFind(new Book(0, "Чистый Agile", 2019,
                new Author(1, "Роберт", "Мартин"),
                new Genre(1, "Техническая литература")));

        repositoryJpa.delete(newBook);

        var actual = em.find(Book.class, newBook.getId());
        assertThat(actual).isNull();
    }

}