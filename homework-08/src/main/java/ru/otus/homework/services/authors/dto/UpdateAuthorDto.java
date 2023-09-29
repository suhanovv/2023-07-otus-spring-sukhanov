package ru.otus.homework.services.authors.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateAuthorDto {
    private final String id;

    private final String firstName;

    private final String lastName;
}
