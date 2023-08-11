package ru.otus.homework02.mappers;


import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.Question;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class QuestionMapperImpl implements QuestionMapper {
    public String mapToString(Question question) {
        List<String> questionAndAnswers = new ArrayList<>();
        questionAndAnswers.add(question.getQuestionText());
        String newLine = System.getProperty("line.separator");


       question.getAnswers().stream()
               .sorted(Comparator.comparing(Answer::getNumber))
               .filter(i -> !i.getAnswerText().isEmpty())
               .forEach(i -> questionAndAnswers.add("    " + i.getNumber() + ") " + i.getAnswerText()));

        return String.join(newLine, questionAndAnswers);

    }
}
