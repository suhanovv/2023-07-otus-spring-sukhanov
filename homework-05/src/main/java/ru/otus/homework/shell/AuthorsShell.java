package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.converters.AuthorsListToStringConverter;
import ru.otus.homework.services.authors.CreateAuthorService;
import ru.otus.homework.services.authors.FindAuthorService;
import ru.otus.homework.services.authors.ModifyAuthorService;
import ru.otus.homework.services.authors.RemoveAuthorService;
import ru.otus.homework.services.authors.dto.CreateAuthorDto;
import ru.otus.homework.services.authors.dto.UpdateAuthorDto;
import ru.otus.homework.services.authors.exceptions.AuthorAlreadyUsedException;
import ru.otus.homework.services.authors.exceptions.AuthorNotFoundException;

@ShellComponent
@RequiredArgsConstructor
public class AuthorsShell {
    private final FindAuthorService findService;

    private final CreateAuthorService createService;

    private final ModifyAuthorService modifyService;

    private final RemoveAuthorService removeService;

    private final AuthorsListToStringConverter conversionService;

    @ShellMethod
    public String listAuthors() {
        return conversionService.convert(findService.getAll());
    }

    @ShellMethod
    public String showAuthor(@ShellOption long id) {
        try {
            return findService.get(id).toString();
        } catch (AuthorNotFoundException e) {
            return "Author not found";
        }
    }

    @ShellMethod
    public String createAuthor(@ShellOption String firstName, @ShellOption String lastName) {
        var input = new CreateAuthorDto(firstName, lastName);
        var newAuthor = createService.createFromInput(input);
        return "Author created: " + newAuthor;
    }

    @ShellMethod
    public String updateAuthor(
            @ShellOption long id,
            @ShellOption(defaultValue = ShellOption.NULL) String firstName,
            @ShellOption(defaultValue = ShellOption.NULL) String lastName) {

        UpdateAuthorDto input = new UpdateAuthorDto(id, firstName, lastName);
        try {
            var updatedAuthor = modifyService.modifyFromInput(input);
            return "Author " + updatedAuthor + " updated";
        } catch (AuthorNotFoundException e) {
            return "Author not found";
        }
    }

    @ShellMethod
    public String removeAuthor(@ShellOption long id) {
        try {
            removeService.remove(id);
            return "Author with id: " + id + " removed";
        } catch (AuthorAlreadyUsedException e) {
            return "Author can't be deleted";
        } catch (AuthorNotFoundException e) {
            return "Author not found";
        }
    }
}
