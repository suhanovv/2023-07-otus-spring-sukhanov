package ru.otus.homework.services.genres.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateGenreDto {
    private final String name;
}
