package ru.otus.homework.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {

    @Autowired
    private GenreRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @Test
    void insertShouldCreateNewGenre() {
        var expectedGenre = new Genre(0, "Ужасные ужасы");
        var query = em.getEntityManager().createQuery("select g from Genre g", Genre.class);
        var genresBeforeCreate = query.getResultList();

        var actualGenre = repositoryJpa.insert(expectedGenre);

        assertThat(genresBeforeCreate)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .doesNotContain(expectedGenre);
        assertThat(actualGenre).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedGenre);
    }

    @Test
    void updateShouldUpdateGenre() {
        var expectedGenre = new Genre(1, "Техническая литература");
        var oldGenre = em.find(Genre.class, 1);
        assertThat(oldGenre).usingRecursiveComparison().isEqualTo(expectedGenre);

        expectedGenre.setName("Фольклор");
        repositoryJpa.update(expectedGenre);

        var actualGenre = em.find(Genre.class, 1);
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    void getByIdShouldReturnExpectedGenre() {
        var expectedGenre = em.find(Genre.class, 1);

        var actualGenre = repositoryJpa.getById(1).orElseThrow();

        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    void getAllShouldReturnExpectedGenreList() {
        var expectedGenre1 = new Genre(1, "Техническая литература");
        var expectedGenre2 = new Genre(2, "Сказки про код");

        var genresList = repositoryJpa.getAll();

        assertThat(genresList).containsExactlyInAnyOrder(expectedGenre1, expectedGenre2);
    }

    @Test
    void deleteByIdShouldDeleteCreatedGenre() {
        var newGenre = em.persistFlushFind(new Genre(0, "Проза"));

        repositoryJpa.delete(newGenre);

        var actual = em.find(Genre.class, newGenre.getId());
        assertThat(actual).isNull();
    }

}