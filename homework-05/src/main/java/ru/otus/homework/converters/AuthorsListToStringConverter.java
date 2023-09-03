package ru.otus.homework.converters;

import org.springframework.stereotype.Component;
import ru.otus.homework.domain.Author;

import java.util.List;

@Component
public class AuthorsListToStringConverter {
    public String convert(List<Author> source) {
        String newLine = System.getProperty("line.separator");
        return source.stream()
                .map(Author::toString)
                .reduce("",
                        (partial, elem) -> String.join(newLine, partial, elem));

    }
}
