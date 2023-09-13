package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.converters.CommentsListToStringConverter;
import ru.otus.homework.services.books.exceptions.BookNotFoundException;
import ru.otus.homework.services.comments.CommentService;
import ru.otus.homework.services.comments.dto.CreateCommentDto;
import ru.otus.homework.services.comments.dto.UpdateCommentDto;
import ru.otus.homework.services.comments.exceptions.CommentNotFoundException;

@ShellComponent
@RequiredArgsConstructor
public class CommentShell {
    private final CommentService commentService;

    private final CommentsListToStringConverter listConversionService;

    private final ConversionService conversionService;

    @ShellMethod(value = "Display comment", key = {"show-comment"})
    public String showComment(@ShellOption long id) {
        try {
            return conversionService.convert(commentService.get(id), String.class);
        } catch (CommentNotFoundException e) {
            return "Book not found";
        }
    }

    @ShellMethod(value = "Show book comments", key = {"show-book-comments"})
    public String showBookComments(@ShellOption long bookId) {
        try {
            return listConversionService.convert(commentService.getByBookId(bookId));
        } catch (BookNotFoundException e) {
            return "Book not found";
        }
    }

    @ShellMethod(value = "Create new comment", key = {"create-comment"})
    public String createComment(@ShellOption long bookId, @ShellOption String comment) {
        var input = new CreateCommentDto(comment, bookId);
        try {
            var newComment = commentService.create(input);
            return "Comment created " + conversionService.convert(newComment, String.class);
        } catch (BookNotFoundException e) {
            return "Book not found";
        }
    }

    @ShellMethod(value = "Update comment", key = {"update-comment"})
    public String updateComment(
            @ShellOption long id,
            @ShellOption String comment) {

        var input = new UpdateCommentDto(id, comment);
        try {
            commentService.update(input);
            return "Comment updated";
        } catch (CommentNotFoundException e) {
            return "Comment not found";
        }
    }

    @ShellMethod(value = "Delete comment", key = {"remove-comment"})
    public String removeComment(@ShellOption long id) {
        commentService.remove(id);
        return "Comment with id: " + id + " removed";
    }

    
}
