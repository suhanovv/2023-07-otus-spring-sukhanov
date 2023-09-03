package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.converters.GenresListToStringConverter;
import ru.otus.homework.services.genres.CreateGenreService;
import ru.otus.homework.services.genres.FindGenreService;
import ru.otus.homework.services.genres.ModifyGenreService;
import ru.otus.homework.services.genres.RemoveGenreService;
import ru.otus.homework.services.genres.dto.CreateGenreDto;
import ru.otus.homework.services.genres.dto.UpdateGenreDto;
import ru.otus.homework.services.genres.exceptions.GenreAlreadyUsedException;
import ru.otus.homework.services.genres.exceptions.GenreNotFoundException;

@ShellComponent
@RequiredArgsConstructor
public class GenresShell {

    private final FindGenreService findService;

    private final CreateGenreService createService;

    private final ModifyGenreService modifyService;

    private final RemoveGenreService removeService;

    private final GenresListToStringConverter conversionService;


    @ShellMethod
    public String listGenres() {
        return conversionService.convert(findService.getAll());
    }

    @ShellMethod
    public String showGenre(@ShellOption long id) {
        try {
            return findService.get(id).toString();
        } catch (GenreNotFoundException e) {
            return "Genre not found";
        }
    }

    @ShellMethod
    public String createGenre(@ShellOption String name) {
        var input = new CreateGenreDto(name);
        var newGenre = createService.createFromInput(input);

        return "Genre created " + newGenre;
    }

    @ShellMethod
    public String updateGenre(
            @ShellOption long id,
            @ShellOption(defaultValue = ShellOption.NULL) String name) {

        var input = new UpdateGenreDto(id, name);
        try {
            var updatedGenre = modifyService.modifyFromInput(input);
            return "Genre " + updatedGenre + " updated";
        } catch (GenreNotFoundException e) {
            return "Genre not found";
        }
    }

    @ShellMethod
    public String removeGenre(@ShellOption long id) {
        try {
            removeService.remove(id);
            return "Genre with id: " + id + " removed";
        } catch (GenreAlreadyUsedException e) {
            return "Genre can't be delete";
        }  catch (GenreNotFoundException e) {
            return "Genre not found";
        }
    }
}
