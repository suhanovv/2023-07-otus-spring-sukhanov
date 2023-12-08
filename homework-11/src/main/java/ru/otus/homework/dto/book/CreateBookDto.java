package ru.otus.homework.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookDto {
    @NotBlank(message = "Title should'n be empty")
    private String title;

    @NotNull(message = "Year should'n be empty")
    @PastOrPresent(message = "Year should present or past")
    private Year year;

    @NotNull(message = "Author should'n be empty")
    private String authorId;

    @NotNull(message = "Genre should'n be empty")
    private String genreId;
}
