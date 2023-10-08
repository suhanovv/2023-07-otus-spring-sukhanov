package ru.otus.homework.services.comments;

import ru.otus.homework.models.Comment;
import ru.otus.homework.services.books.exceptions.BookNotFoundException;
import ru.otus.homework.services.comments.dto.CreateCommentDto;
import ru.otus.homework.services.comments.dto.UpdateCommentDto;
import ru.otus.homework.services.comments.exceptions.CommentNotFoundException;

import java.util.List;

public interface CommentService {
    Comment create(CreateCommentDto comment) throws BookNotFoundException;

    Comment get(long id) throws CommentNotFoundException;

    void update(UpdateCommentDto comment) throws CommentNotFoundException;

    void remove(long id) throws CommentNotFoundException;

    List<Comment> getByBookId(long bookId);
}
