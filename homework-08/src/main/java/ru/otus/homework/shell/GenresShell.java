package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.converters.GenresListToStringConverter;
import ru.otus.homework.services.genres.GenreService;
import ru.otus.homework.services.genres.dto.CreateGenreDto;
import ru.otus.homework.services.genres.dto.UpdateGenreDto;
import ru.otus.homework.services.genres.exceptions.GenreAlreadyUsedException;
import ru.otus.homework.services.genres.exceptions.GenreNotFoundException;

@ShellComponent
@RequiredArgsConstructor
public class GenresShell {

    private final GenreService genreService;

    private final GenresListToStringConverter listConversionService;

    private final ConversionService conversionService;


    @ShellMethod(value = "Show all genres", key = {"list-genres"})
    public String listGenres() {
        return listConversionService.convert(genreService.getAll());
    }

    @ShellMethod(value = "Display genre", key = {"show-genre"})
    public String showGenre(@ShellOption String id) {
        try {
            return conversionService.convert(genreService.get(id), String.class);
        } catch (GenreNotFoundException e) {
            return "Genre not found";
        }
    }

    @ShellMethod(value = "Create new genre", key = {"create-genre"})
    public String createGenre(@ShellOption String name) {
        var input = new CreateGenreDto(name);
        var newGenre = genreService.create(input);

        return "Genre created " + conversionService.convert(newGenre, String.class);
    }

    @ShellMethod(value = "Update genre", key = {"update-genre"})
    public String updateGenre(
            @ShellOption String id,
            @ShellOption(defaultValue = ShellOption.NULL) String name) {

        var input = new UpdateGenreDto(id, name);
        try {
            var updatedGenre = genreService.modify(input);
            return "Genre " + conversionService.convert(updatedGenre, String.class) + " updated";
        } catch (GenreNotFoundException e) {
            return "Genre not found";
        }
    }

    @ShellMethod(value = "Delete genre", key = {"remove-genre"})
    public String removeGenre(@ShellOption String id) {
        try {
            genreService.remove(id);
            return "Genre with id: " + id + " removed";
        } catch (GenreAlreadyUsedException e) {
            return "Genre can't be delete";
        } catch (GenreNotFoundException e) {
            return "Genre not found";
        }
    }
}
