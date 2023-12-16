package ru.otus.homework.dto.genre;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.homework.models.Genre;

@Data
@AllArgsConstructor
public class GenreDto {
    private final String id;

    private final String name;

    public static GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }

    public static Genre toDomain(GenreDto dto) {
        return new Genre(dto.getId(), dto.getName());
    }
}
