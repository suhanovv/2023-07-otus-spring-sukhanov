package ru.otus.homework.dto.book;

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

    @NotBlank(message = "Author should'n be empty")
    private final String authorId;

    @NotBlank(message = "Genre should'n be empty")
    private final String genreId;
}
