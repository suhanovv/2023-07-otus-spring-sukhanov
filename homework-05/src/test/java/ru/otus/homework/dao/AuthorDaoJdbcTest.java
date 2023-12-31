package ru.otus.homework.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.homework.domain.Author;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDaoJdbc dao;


    @Test
    void insertShouldCreateNewAuthor() {
        var expectedAuthor = new Author("Василий", "Петров");
        var authors = dao.getAll();

        assertThat(authors)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .doesNotContain(expectedAuthor);

        var actualAuthor = dao.insert(expectedAuthor);
        assertThat(actualAuthor).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedAuthor);
    }

    @Test
    void updateShouldUpdateAuthor() {
        var expectedAuthor = new Author(1, "Роберт", "Мартин");
        var oldAuthor = dao.getById(1);
        assertThat(oldAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);

        expectedAuthor.setLastName("Половинкин");

        dao.update(expectedAuthor);

        var actualAuthor = dao.getById(1);

        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    void getByIdShouldReturnExpectedAuthor() {
        var expectedAuthor = new Author(1, "Роберт", "Мартин");

        var author = dao.getById(1);

        assertThat(author).usingRecursiveComparison().isEqualTo(expectedAuthor);

    }

    @Test
    void getAllShouldReturnExpectedAuthorList() {
        var expectedAuthor1 = new Author(1, "Роберт", "Мартин");
        var expectedAuthor2 = new Author(2, "Сказочник", "Петров");

        var authorsList = dao.getAll();

        assertThat(authorsList).containsExactlyInAnyOrder(expectedAuthor1, expectedAuthor2);
    }

    @Test
    void deleteByIdShouldDeleteCreatedAuthor() {
        var newAuthor = dao.insert(new Author("Василий", "Петров"));

        dao.deleteById(newAuthor.getId());

        assertThatThrownBy(
                () -> dao.getById(newAuthor.getId()))
                .isInstanceOf(EmptyResultDataAccessException.class);

    }

}