package ru.otus.homework02.converters;


import org.springframework.core.convert.converter.Converter;
import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.Question;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class QuestionToStringConverter implements Converter<Question, String> {
    @Override
    public String convert(Question source) {
        List<String> questionAndAnswers = new ArrayList<>();
        questionAndAnswers.add(source.getQuestionText());
        String newLine = System.getProperty("line.separator");


        source.getAnswers().stream()
                .sorted(Comparator.comparing(Answer::getId))
                .filter(i -> !i.getAnswerText().isEmpty())
                .forEach(i -> questionAndAnswers.add("    " + i.getId() + ") " + i.getAnswerText()));

        return String.join(newLine, questionAndAnswers);
    }


}