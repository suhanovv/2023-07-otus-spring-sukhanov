package ru.otus.homework.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import ru.otus.homework.domain.Author;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorsListToStringConverter {

    private final ConversionService conversionService;

    public String convert(List<Author> source) {
        String newLine = System.getProperty("line.separator");
        return source.stream()
                .map(i -> conversionService.convert(i, String.class))
                .reduce("",
                        (partial, elem) -> String.join(newLine, partial, elem));

    }
}
