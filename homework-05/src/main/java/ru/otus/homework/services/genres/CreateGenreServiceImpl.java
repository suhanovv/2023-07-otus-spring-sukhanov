package ru.otus.homework.services.genres;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.GenreDao;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.services.genres.dto.CreateGenreDto;

@Service
@RequiredArgsConstructor
public class CreateGenreServiceImpl implements CreateGenreService {

    private final GenreDao dao;

    @Override
    public Genre createFromInput(CreateGenreDto input) {
        var genre = new Genre(input.getName());
        return dao.insert(genre);
    }
}
