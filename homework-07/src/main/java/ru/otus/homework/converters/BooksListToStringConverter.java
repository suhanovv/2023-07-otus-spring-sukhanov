package ru.otus.homework.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.homework.models.Book;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BooksListToStringConverter {

    private final BookToStringConverter converter;

    public String convert(List<Book> source) {
        String newLine = System.getProperty("line.separator");
        return source.stream()
                .map(converter::convert)
                .reduce("",
                        (partial, elem) -> String.join(newLine, partial, elem));

    }
}
