package ru.otus.homework.services.genres;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.GenreDao;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.services.genres.dto.UpdateGenreDto;
import ru.otus.homework.services.genres.exceptions.GenreNotFoundException;

@Service
@RequiredArgsConstructor
public class ModifyGenreServiceImpl implements ModifyGenreService {

    private final GenreDao dao;

    private final FindGenreService findService;

    @Override
    public Genre modifyFromInput(UpdateGenreDto input) throws GenreNotFoundException {
        var genre = findService.get(input.getId());

        if (input.getName() != null) {
            genre.setName(input.getName());
        }
        dao.update(genre);

        return genre;
    }
}
