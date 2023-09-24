package ru.otus.homework.repositories;

import org.springframework.data.repository.ListCrudRepository;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Comment;

import java.util.List;

public interface CommentRepository extends ListCrudRepository<Comment, Long> {
    List<Comment> findAllByBook(Book book);
}
