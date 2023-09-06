package ru.otus.homework.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import ru.otus.homework.domain.Genre;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GenresListToStringConverter {

    private final ConversionService conversionService;

    public String convert(List<Genre> source) {
        String newLine = System.getProperty("line.separator");
        return source.stream()
                .map(i -> conversionService.convert(i, String.class))
                .reduce("",
                        (partial, elem) -> String.join(newLine, partial, elem));

    }
}
