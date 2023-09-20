package ru.otus.homework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.homework.models.Comment;

@Component
public class CommentToStringConverter implements Converter<Comment, String> {
    @Override
    public String convert(Comment source) {
        return "Id=" + source.getId() + " " + source.getCommentText();
    }
}
