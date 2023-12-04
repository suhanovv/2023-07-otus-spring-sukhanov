package ru.otus.homework.services.books.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Year;

@Data
@AllArgsConstructor
public class UpdateBookDto {
    @NotBlank(message = "Title should'n be empty")
    private final String title;

    @NotNull(message = "Year should'n be empty")
    @PastOrPresent(message = "Year should present or past")
    private final Year year;

    @NotNull(message = "Author should'n be empty")
    private final long authorId;

    @NotNull(message = "Genre should'n be empty")
    private final long genreId;
}
