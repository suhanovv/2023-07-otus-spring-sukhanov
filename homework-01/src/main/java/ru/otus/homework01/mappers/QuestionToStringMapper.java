package ru.otus.homework01.mappers;

import ru.otus.homework01.domain.Answer;
import ru.otus.homework01.domain.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public final class QuestionToStringMapper {
    public static String map(Question question) {
        List<String> questionAndAnswers = new ArrayList<>();
        questionAndAnswers.add(question.getQuestionText());
        String newLine = System.getProperty("line.separator");

        ListIterator<Answer> answerIt = question.getAnswers().listIterator();

        while (answerIt.hasNext()) {
            int index = answerIt.nextIndex() + 1;
            Answer answerText = answerIt.next();
            if (answerText.getAnswerText().length() == 0) {
                continue;
            }
            questionAndAnswers.add("    " + index + ") " + answerText.getAnswerText());
        }

        return String.join(newLine, questionAndAnswers);

    }
}
