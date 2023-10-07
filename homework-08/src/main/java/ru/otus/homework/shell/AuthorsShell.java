package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.converters.AuthorsListToStringConverter;
import ru.otus.homework.services.authors.AuthorService;
import ru.otus.homework.services.authors.dto.CreateAuthorDto;
import ru.otus.homework.services.authors.dto.UpdateAuthorDto;
import ru.otus.homework.services.authors.exceptions.AuthorAlreadyUsedException;
import ru.otus.homework.services.authors.exceptions.AuthorNotFoundException;

@ShellComponent
@RequiredArgsConstructor
public class AuthorsShell {
    private final AuthorService authorService;

    private final AuthorsListToStringConverter listConversionService;

    private final ConversionService conversionService;

    @ShellMethod(value = "Show all authors", key = {"list-authors"})
    public String listAuthors() {
        return listConversionService.convert(authorService.getAll());
    }

    @ShellMethod(value = "Display author", key = {"show-author"})
    public String showAuthor(@ShellOption String id) {
        try {
            return conversionService.convert(authorService.get(id), String.class);
        } catch (AuthorNotFoundException e) {
            return "Author not found";
        }
    }

    @ShellMethod(value = "Create new author", key = {"create-author"})
    public String createAuthor(@ShellOption String firstName, @ShellOption String lastName) {
        var input = new CreateAuthorDto(firstName, lastName);
        var newAuthor = authorService.create(input);
        return "Author created: " + conversionService.convert(newAuthor, String.class);
    }

    @ShellMethod(value = "Update author", key = {"update-author"})
    public String updateAuthor(
            @ShellOption String id,
            @ShellOption(defaultValue = ShellOption.NULL) String firstName,
            @ShellOption(defaultValue = ShellOption.NULL) String lastName) {

        UpdateAuthorDto input = new UpdateAuthorDto(id, firstName, lastName);
        try {
            var updatedAuthor = authorService.modify(input);
            return "Author " + conversionService.convert(updatedAuthor, String.class) + " updated";
        } catch (AuthorNotFoundException e) {
            return "Author not found";
        }
    }

    @ShellMethod(value = "Delete author", key = {"remove-author"})
    public String removeAuthor(@ShellOption String id) {
        try {
            authorService.remove(id);
            return "Author with id: " + id + " removed";
        } catch (AuthorAlreadyUsedException e) {
            return "Author can't be deleted";
        } catch (AuthorNotFoundException e) {
            return "Author not found";
        }
    }
}
