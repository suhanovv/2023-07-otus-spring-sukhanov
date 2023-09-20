package ru.otus.homework.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    private CommentRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    void findAllByBook() {
        var book = em.find(Book.class, 1);
        var comment1 = em.persistFlushFind(new Comment(0, book, "Comment 1"));
        var comment2 = em.persistFlushFind(new Comment(0, book, "Comment 2"));

        var expected = repository.findAllByBook(book);

        assertThat(expected).containsExactlyInAnyOrder(comment1, comment2).usingRecursiveComparison();

    }
}