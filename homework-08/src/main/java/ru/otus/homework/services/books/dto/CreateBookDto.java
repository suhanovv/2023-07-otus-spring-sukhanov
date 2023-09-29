package ru.otus.homework.services.books.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateBookDto {
    private final String title;

    private final int year;

    private final String authorId;

    private final String genreId;
}
