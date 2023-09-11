package ru.otus.homework.services.genres.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateGenreDto {
    private final long id;

    private final String name;
}
