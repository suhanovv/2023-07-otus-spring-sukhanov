package ru.otus.homework.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(CommentRepositoryJpa.class)
class CommentRepositoryJpaTest {

    @Autowired
    private CommentRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @Test
    void insertShouldCreateNewComment() {
        var book = em.find(Book.class, 1);
        var expectedComment = new Comment(0, book, "комментарий");
        var query = em.getEntityManager().createQuery("select с from Comment с", Comment.class);
        var commentsBeforeCreate = query.getResultList();

        var actualComment = repositoryJpa.insert(expectedComment);

        assertThat(commentsBeforeCreate)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .doesNotContain(expectedComment);
        assertThat(actualComment).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedComment);

    }

    @Test
    void updateShouldUpdateComment() {
        var book = em.find(Book.class, 1);
        var newComment = em.persistFlushFind(new Comment(0, book,"Новый комментарий"));
        var expectedCommentText = "Обновленный комментарий";
        newComment.setCommentText(expectedCommentText);

        repositoryJpa.update(newComment);

        var actualComment = em.find(Comment.class, newComment.getId());
        assertThat(actualComment.getCommentText()).isEqualTo(expectedCommentText);

    }

    @Test
    void getByIdShouldReturnExpectedComment() {
        var book = em.find(Book.class, 1);
        var newComment = em.persist(new Comment(0, book, "Коммент"));

        var actualComment = repositoryJpa.getById(newComment.getId());

        assertThat(actualComment.orElseThrow()).usingRecursiveComparison().isEqualTo(newComment);

    }

    @Test
    void findByBookShouldReturnExpectedBookComments() {
        var book = em.find(Book.class, 1);
        var newComment1 = em.persistFlushFind(new Comment(0, book, "Комментарий один"));
        var newComment2 = em.persistFlushFind(new Comment(0, book, "Комментарий два"));

        var actualComments = repositoryJpa.findByBook(book);

        assertThat(actualComments).containsExactlyInAnyOrder(newComment1, newComment2).usingRecursiveComparison();

    }

    @Test
    void deleteById() {
        var book = em.find(Book.class, 1);
        var newComment = em.persistFlushFind(new Comment(0, book, "Комментарий"));

        repositoryJpa.deleteById(newComment.getId());
        em.detach(newComment);

        var actualComment = em.find(Comment.class, newComment.getId());
        assertThat(actualComment).isNull();
    }
}