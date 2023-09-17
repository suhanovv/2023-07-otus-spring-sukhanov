package ru.otus.homework.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.homework.models.Genre;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GenresListToStringConverter {

    private final GenreToStringConverter converter;

    public String convert(List<Genre> source) {
        String newLine = System.getProperty("line.separator");
        return source.stream()
                .map(converter::convert)
                .reduce("",
                        (partial, elem) -> String.join(newLine, partial, elem));

    }
}
