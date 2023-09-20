package ru.otus.homework.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Comment;

import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Comment insert(Comment comment) {
        em.persist(comment);
        return comment;
    }

    @Override
    public Comment update(Comment comment) {
        return em.merge(comment);
    }

    @Override
    public Optional<Comment> getById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> findByBook(Book book) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c where c.book.id = :book_id", Comment.class);
        query.setParameter("book_id", book.getId());

        return query.getResultList();

    }

    @Override
    public void delete(Comment comment) {
        em.remove(comment);
    }
}
