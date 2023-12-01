package ru.otus.homework.services.authors.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateAuthorDto {
    @NotBlank(message = "First name should'n be empty")
    private final String firstName;

    @NotBlank(message = "Last name should'n be empty")
    private final String lastName;
}
