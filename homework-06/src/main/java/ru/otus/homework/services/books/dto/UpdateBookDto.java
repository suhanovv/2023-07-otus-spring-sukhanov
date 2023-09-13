package ru.otus.homework.services.books.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateBookDto {
    private final long id;

    private final String title;

    private final int year;

    private final long authorId;

    private final long genreId;
}
