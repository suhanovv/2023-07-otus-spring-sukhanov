package ru.otus.homework01.services;

import ru.otus.homework01.dao.TestDao;
import ru.otus.homework01.domain.Question;
import ru.otus.homework01.domain.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class TestServiceImpl implements TestService {
    private final TestDao dao;

    public TestServiceImpl(TestDao dao) {
        this.dao = dao;
    }

    @Override
    public void showTest() {
        Test test = dao.loadTest();
        List<String> questionsAndAnswers = new ArrayList<>();
        String newLine = System.getProperty("line.separator");

        for (Question question : test.getQuestions()) {
            questionsAndAnswers.add(question.getQuestionText());
            ListIterator<String> answerIt = question.getAnswers().listIterator();

            while (answerIt.hasNext()) {
                int index = answerIt.nextIndex() + 1;
                String answerText = answerIt.next();
                if (answerText.length() == 0) {
                    continue;
                }
                questionsAndAnswers.add("    " + index + ") " + answerText);
            }
        }

        System.out.print(String.join(newLine, questionsAndAnswers));
    }
}
