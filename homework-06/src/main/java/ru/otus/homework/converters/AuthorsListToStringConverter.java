package ru.otus.homework.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.homework.models.Author;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorsListToStringConverter {

    private final AuthorToStringConverter converter;

    public String convert(List<Author> source) {
        String newLine = System.getProperty("line.separator");
        return source.stream()
                .map(converter::convert)
                .reduce("",
                        (partial, elem) -> String.join(newLine, partial, elem));

    }
}
