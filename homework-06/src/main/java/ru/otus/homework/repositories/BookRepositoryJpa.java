package ru.otus.homework.repositories;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.homework.models.Book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Repository
@AllArgsConstructor
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Book insert(Book book) {
        em.persist(book);
        return book;
    }

    @Override
    public Book update(Book book) {
        return em.merge(book);
    }

    @Override
    public Optional<Book> getById(long id) {
        EntityGraph<?> eg = em.getEntityGraph("book-author-genre-entity-graph");
        Map<String, Object> properties = new HashMap<>();
        properties.put("jakarta.persistence.fetchgraph", eg);
        return Optional.ofNullable(em.find(Book.class, id, properties));

    }

    @Override
    public List<Book> getAll() {
        EntityGraph<?> eg = em.getEntityGraph("book-author-genre-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        query.setHint(FETCH.getKey(), eg);
        return query.getResultList();
    }

    @Override
    public void delete(Book book) {
        em.remove(book);
    }

}
