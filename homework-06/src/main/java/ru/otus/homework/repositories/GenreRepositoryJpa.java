package ru.otus.homework.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.homework.models.Genre;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Genre insert(Genre genre) {
        em.persist(genre);
        return genre;
    }

    @Override
    public Genre update(Genre genre) {

        return em.merge(genre);
    }

    @Override
    public Optional<Genre> getById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = em.createQuery(" select g from Genre g ", Genre.class);
        return query.getResultList();
    }

    @Override
    public void delete(Genre genre) {
        em.remove(genre);
    }
}
