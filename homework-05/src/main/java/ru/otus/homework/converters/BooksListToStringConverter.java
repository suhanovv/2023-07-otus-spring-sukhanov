package ru.otus.homework.converters;

import org.springframework.stereotype.Component;
import ru.otus.homework.domain.Book;

import java.util.List;

@Component
public class BooksListToStringConverter {

    public String convert(List<Book> source) {
        String newLine = System.getProperty("line.separator");
        return source.stream()
                .map(Book::toString)
                .reduce("",
                        (partial, elem) -> String.join(newLine, partial, elem));

    }
}
