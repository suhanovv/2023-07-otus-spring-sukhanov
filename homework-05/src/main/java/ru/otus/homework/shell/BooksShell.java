package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.converters.BooksListToStringConverter;
import ru.otus.homework.services.authors.exceptions.AuthorNotFoundException;
import ru.otus.homework.services.books.CreateBookService;
import ru.otus.homework.services.books.FindBookService;
import ru.otus.homework.services.books.ModifyBookService;
import ru.otus.homework.services.books.RemoveBookService;
import ru.otus.homework.services.books.dto.CreateBookDto;
import ru.otus.homework.services.books.dto.UpdateBookDto;
import ru.otus.homework.services.books.exceptions.BookNotFoundException;
import ru.otus.homework.services.books.exceptions.ModifyBookException;
import ru.otus.homework.services.genres.exceptions.GenreNotFoundException;

@ShellComponent
@RequiredArgsConstructor
public class BooksShell {
    private final FindBookService findService;

    private final CreateBookService createService;

    private final ModifyBookService modifyService;

    private final RemoveBookService removeService;

    private final BooksListToStringConverter conversionService;

    @ShellMethod
    public String listBooks() {
        return conversionService.convert(findService.getAll());
    }

    @ShellMethod
    public String showBook(@ShellOption long id) {
        try {
            return findService.get(id).toString();
        } catch (BookNotFoundException e) {
            return "Book not found";
        }
    }

    @ShellMethod
    public String createBook(
            @ShellOption String title,
            @ShellOption int year,
            @ShellOption long authorId,
            @ShellOption long genreId) {
        var input = new CreateBookDto(title, year, authorId, genreId);
        var newBook = createService.createFromInput(input);

        return "Book created " + newBook;
    }

    @ShellMethod
    public String updateBook(
            @ShellOption long id,
            @ShellOption(defaultValue = ShellOption.NULL) String title,
            @ShellOption(defaultValue = "0") int year,
            @ShellOption(defaultValue = "0") long authorId,
            @ShellOption(defaultValue = "0") long genreId) {

        var input = new UpdateBookDto(id, title, year, authorId, genreId);
        try {
            var updatedBook = modifyService.modifyFromInput(input);
            return "Book " + updatedBook + " updated";
        } catch (BookNotFoundException e) {
            return "Book not found";
        } catch (ModifyBookException e) {
            var cause = e.getCause();
            if (cause.getClass() == GenreNotFoundException.class) {
                return "Genre not found";
            } else if (cause.getClass() == AuthorNotFoundException.class) {
                return "Author not found";
            }
            return "Book update error";
        }
    }

    @ShellMethod
    public String removeBook(@ShellOption long id) {
        try {
            removeService.remove(id);
            return "Book with id: " + id + " removed";
        } catch (BookNotFoundException e) {
            return "Book not found";
        }
    }

}
