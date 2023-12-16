package ru.otus.homework.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.homework.models.Book;
import ru.otus.homework.dto.author.AuthorDto;
import ru.otus.homework.dto.genre.GenreDto;

import java.time.Year;

@Data
@AllArgsConstructor
public class BookDto {
    private final String id;

    private final String title;

    private final Year year;

    private final AuthorDto author;

    private final GenreDto genre;

    public static BookDto toDto(Book book) {
        return new BookDto(
                book.getId(), book.getTitle(), book.getPublishYear(),
                AuthorDto.toDto(book.getAuthor()),GenreDto.toDto(book.getGenre()));
    }

    public static Book toDomain(BookDto dto) {
        return new Book(dto.getId(), dto.getTitle(), dto.getYear(),
                AuthorDto.toDomain(dto.getAuthor()), GenreDto.toDomain(dto.getGenre()));
    }
}
