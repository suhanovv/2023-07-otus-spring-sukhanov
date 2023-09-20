package ru.otus.homework.repositories;

import ru.otus.homework.models.Book;
import ru.otus.homework.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment insert(Comment comment);

    Comment update(Comment comment);

    Optional<Comment> getById(long id);

    List<Comment> findByBook(Book book);

    void delete(Comment comment);
}
