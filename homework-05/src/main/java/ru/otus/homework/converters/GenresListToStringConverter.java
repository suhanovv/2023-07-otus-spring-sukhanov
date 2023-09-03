package ru.otus.homework.converters;

import org.springframework.stereotype.Component;
import ru.otus.homework.domain.Genre;

import java.util.List;

@Component
public class GenresListToStringConverter {

    public String convert(List<Genre> source) {
        String newLine = System.getProperty("line.separator");
        return source.stream()
                .map(Genre::toString)
                .reduce("",
                        (partial, elem) -> String.join(newLine, partial, elem));

    }
}
