package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.converters.BooksListToStringConverter;
import ru.otus.homework.services.authors.exceptions.AuthorNotFoundException;
import ru.otus.homework.services.books.BookService;
import ru.otus.homework.services.books.dto.CreateBookDto;
import ru.otus.homework.services.books.dto.UpdateBookDto;
import ru.otus.homework.services.books.exceptions.BookNotFoundException;
import ru.otus.homework.services.books.exceptions.CreateBookException;
import ru.otus.homework.services.books.exceptions.ModifyBookException;
import ru.otus.homework.services.genres.exceptions.GenreNotFoundException;

@ShellComponent
@RequiredArgsConstructor
public class BooksShell {
    private final BookService bookService;

    private final BooksListToStringConverter listConversionService;

    private final ConversionService conversionService;

    @ShellMethod(value = "Show all books", key = {"list-books"})
    public String listBooks() {
        return listConversionService.convert(bookService.getAll());
    }

    @ShellMethod(value = "Display book", key = {"show-book"})
    public String showBook(@ShellOption long id) {
        try {
            return conversionService.convert(bookService.get(id), String.class);
        } catch (BookNotFoundException e) {
            return "Book not found";
        }
    }

    @ShellMethod(value = "Create new book", key = {"create-book"})
    public String createBook(
            @ShellOption String title,
            @ShellOption int year,
            @ShellOption long authorId,
            @ShellOption long genreId) {
        var input = new CreateBookDto(title, year, authorId, genreId);

        try {
            var newBook = bookService.create(input);
            return "Book created " + conversionService.convert(newBook, String.class);
        } catch (CreateBookException e) {
            var cause = e.getCause();
            if (cause.getClass() == GenreNotFoundException.class) {
                return "Genre not found";
            } else if (cause.getClass() == AuthorNotFoundException.class) {
                return "Author not found";
            }
            return "Book create error";
        }
    }

    @ShellMethod(value = "Update book", key = {"update-book"})
    public String updateBook(
            @ShellOption long id,
            @ShellOption(defaultValue = ShellOption.NULL) String title,
            @ShellOption(defaultValue = "0") int year,
            @ShellOption(defaultValue = "0") long authorId,
            @ShellOption(defaultValue = "0") long genreId) {

        var input = new UpdateBookDto(id, title, year, authorId, genreId);
        try {
            var updatedBook = bookService.modify(input);
            return "Book " + conversionService.convert(updatedBook, String.class) + " updated";
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

    @ShellMethod(value = "Delete book", key = {"remove-book"})
    public String removeBook(@ShellOption long id) {
        bookService.remove(id);
        return "Book with id: " + id + " removed";

    }

}
