package ru.otus.homework.dto.author;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.otus.homework.models.Author;

@AllArgsConstructor
@Getter
public class AuthorDto {
    private String id;

    private String firstName;

    private String lastName;

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getFirstName(), author.getLastName());
    }

    public static Author toDomain(AuthorDto dto) {
        return new Author(dto.getId(), dto.getFirstName(), dto.getLastName());
    }
}
