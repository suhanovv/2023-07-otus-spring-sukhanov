package ru.otus.homework.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.homework.models.Comment;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentsListToStringConverter {

    private final CommentToStringConverter converter;

    public String convert(List<Comment> source) {
        String newLine = System.getProperty("line.separator");
        return source.stream()
                .map(converter::convert)
                .reduce("",
                        (partial, elem) -> String.join(newLine, partial, elem));

    }
}
