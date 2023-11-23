package ru.otus.homework.services.comments;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.models.Comment;
import ru.otus.homework.repositories.CommentRepository;
import ru.otus.homework.services.books.BookService;
import ru.otus.homework.services.books.dto.BookDto;
import ru.otus.homework.services.comments.dto.CreateCommentDto;
import ru.otus.homework.services.comments.dto.UpdateCommentDto;
import ru.otus.homework.services.comments.exceptions.CommentNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    private final BookService bookService;

    @Transactional
    @Override
    public Comment create(CreateCommentDto comment) {
        var book = BookDto.toDomain(bookService.get(comment.getBookId()));

        var newComment = new Comment(0, book, comment.getCommentText());

        return repository.save(newComment);
    }

    @Transactional(readOnly = true)
    @Override
    public Comment get(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment with id: " + id + " not found"));
    }

    @Transactional
    @Override
    public void update(UpdateCommentDto comment) {
        var oldComment = get(comment.getId());
        oldComment.setCommentText(comment.getCommentText());
        repository.save(oldComment);
    }

    @Transactional
    @Override
    public void remove(long id) {
        var comment = get(id);
        repository.delete(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getByBookId(long bookId) {
        return repository.findAllByBookId(bookId);
    }
}
