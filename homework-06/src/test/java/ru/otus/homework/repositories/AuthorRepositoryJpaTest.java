package ru.otus.homework.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.models.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {

    @Autowired
    private AuthorRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;


    @Test
    void insertShouldCreateNewAuthor() {
        var expectedAuthor = new Author(0, "Василий", "Петров");
        var query = em.getEntityManager().createQuery("select a from Author a", Author.class);
        var authorsBeforeCreate = query.getResultList();

        var actualAuthor = repositoryJpa.insert(expectedAuthor);

        assertThat(authorsBeforeCreate)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .doesNotContain(expectedAuthor);
        assertThat(actualAuthor).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedAuthor);
    }

    @Test
    void updateShouldUpdateAuthor() {
        var expectedAuthor = new Author(1, "Роберт", "Мартин");
        var oldAuthor = em.find(Author.class, 1);
        assertThat(oldAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);

        expectedAuthor.setLastName("Половинкин");
        repositoryJpa.update(expectedAuthor);

        var actualAuthor = em.find(Author.class, 1);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    void getByIdShouldReturnExpectedAuthor() {
        var expectedAuthor = em.find(Author.class, 1);

        var author = repositoryJpa.getById(1).orElseThrow();

        assertThat(author).usingRecursiveComparison().isEqualTo(expectedAuthor);

    }

    @Test
    void getAllShouldReturnExpectedAuthorList() {
        var expectedAuthor1 = new Author(1, "Роберт", "Мартин");
        var expectedAuthor2 = new Author(2, "Сказочник", "Петров");

        var authorsList = repositoryJpa.getAll();

        assertThat(authorsList).containsExactlyInAnyOrder(expectedAuthor1, expectedAuthor2);
    }

    @Test
    void deleteByIdShouldDeleteCreatedAuthor() {
        var newAuthor = em.persistFlushFind(new Author(0, "Василий", "Петров"));

        repositoryJpa.delete(newAuthor);

        var actual = em.getEntityManager().find(Author.class, newAuthor.getId());
        assertThat(actual).isNull();
    }

}