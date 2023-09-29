package ru.otus.homework.services.authors.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateAuthorDto {
    private final String firstName;

    private final String lastName;
}
