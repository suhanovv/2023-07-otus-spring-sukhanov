package ru.otus.homework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.homework.domain.Author;


@Component
public class AuthorToStringConverter implements Converter<Author, String> {
    @Override
    public String convert(Author source) {

        return "Id=" + source.getId() + " " + source.getLastName() + " " + source.getFirstName();
    }
}
