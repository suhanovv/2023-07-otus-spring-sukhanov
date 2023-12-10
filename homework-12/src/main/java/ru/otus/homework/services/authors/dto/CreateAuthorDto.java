package ru.otus.homework.services.authors.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateAuthorDto {

    @NotBlank(message = "First name should'n be empty")
    private final String firstName;

    @NotBlank(message = "Last name should'n be empty")
    private final String lastName;
}
