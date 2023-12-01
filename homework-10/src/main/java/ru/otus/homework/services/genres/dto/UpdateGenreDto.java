package ru.otus.homework.services.genres.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(onConstructor_ = {@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)})
@Data
public class UpdateGenreDto {

    @NotBlank(message = "Name should'n be empty")
    private final String name;
}
