package ru.otus.homework.services.genres.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateGenreDto {

    @NotBlank(message = "Name should'n be empty")
    private final String name;
}
