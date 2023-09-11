package ru.otus.homework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.homework.domain.Book;

@Component
public class BookToStringConverter implements Converter<Book, String> {
    @Override
    public String convert(Book source) {
        var author = source.getAuthor();
        var authorStr = "(Id:" + author.getId() + ") " + author.getLastName() + " " + author.getFirstName();

        var genre = source.getGenre();
        var genreStr = "(Id:" + genre.getId() + ") " + genre.getName();

        return "Id=" + source.getId()
                + " " + source.getTitle()
                + " (" + source.getYear() + ")"
                + " genre: " + genreStr
                + " author: " + authorStr;
    }
}
