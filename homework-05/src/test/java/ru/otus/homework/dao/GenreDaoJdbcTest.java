package ru.otus.homework.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(GenreDaoJdbc.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class GenreDaoJdbcTest {

    @Autowired
    private GenreDaoJdbc dao;

    @Test
    void insertShouldCreateNewGenre() {
        var newGenre = new Genre("Ужасные ужасы");

        var expectedGenre = new Genre(3, "Ужасные ужасы");

        var actualGenre = dao.insert(newGenre);

        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    void updateShouldUpdateGenre() {
        var expectedGenre = new Genre(1, "Техническая литература");
        var oldGenre = dao.getById(1);

        assertThat(oldGenre).usingRecursiveComparison().isEqualTo(expectedGenre);

        expectedGenre.setName("Фольклор");

        dao.update(expectedGenre);

        var actualGenre = dao.getById(1);

        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    void getByIdShouldReturnExpectedGenre() {
        var expectedGenre = new Genre(1, "Техническая литература");

        var actualGenre = dao.getById(1);

        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    void getAllShouldReturnExpectedGenreList() {
        var expectedGenre1 = new Genre(1, "Техническая литература");
        var expectedGenre2 = new Genre(2, "Сказки про код");

        var genresList = dao.getAll();

        assertThat(genresList).containsExactlyInAnyOrder(expectedGenre1, expectedGenre2);
    }

    @Test
    void deleteByIdShouldDeleteCreatedGenre() {
        var newGenre = dao.insert(new Genre("Проза"));

        dao.deleteById(newGenre.getId());

        assertThatThrownBy(
                () -> dao.getById(newGenre.getId()))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void deleteByIdNotFoundGenreThrowException() {

        assertThatThrownBy(
                () -> dao.deleteById(300))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}