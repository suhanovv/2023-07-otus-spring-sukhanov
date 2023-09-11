package ru.otus.homework.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.homework.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    @Autowired
    private GenreDaoJdbc dao;

    @Test
    void insertShouldCreateNewGenre() {
        var expectedGenre = new Genre("Ужасные ужасы");
        var genres = dao.getAll();

        assertThat(genres)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .doesNotContain(expectedGenre);


        var actualGenre = dao.insert(expectedGenre);

        assertThat(actualGenre).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedGenre);
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

}