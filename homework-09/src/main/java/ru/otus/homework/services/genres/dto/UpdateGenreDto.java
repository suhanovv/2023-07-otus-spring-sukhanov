package ru.otus.homework.services.genres.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateGenreDto {
    private final long id;

    @NotBlank(message = "Name should'n be empty")
    private final String name;
}
