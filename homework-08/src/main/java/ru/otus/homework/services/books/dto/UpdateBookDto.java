package ru.otus.homework.services.books.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateBookDto {
    private final String id;

    private final String title;

    private final int year;

    private final String authorId;

    private final String genreId;
}
