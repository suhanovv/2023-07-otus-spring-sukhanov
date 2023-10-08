package ru.otus.homework.services.books.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Year;

@Data
@AllArgsConstructor
public class UpdateBookDto {
    private final long id;

    @NotBlank(message = "Title should'n be empty")
    private final String title;

    @NotNull(message = "Year should'n be empty")
    private final Year year;

    @NotNull(message = "Author should'n be empty")
    private final long authorId;

    @NotNull(message = "Genre should'n be empty")
    private final long genreId;

    public static UpdateBookDto toDto(BookDto book) {
        return new UpdateBookDto(book.getId(), book.getTitle(), book.getYear(),
                book.getAuthor().getId(), book.getGenre().getId());
    }
}
