package ru.otus.homework02.dao;

import ru.otus.homework02.dao.exceptions.TestReadingException;
import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.Question;
import ru.otus.homework02.domain.SimpleTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TestDaoCsv implements TestDao {
    private final String sourcePath;

    private final String colDelimiter;

    private final String answersDelimiter;

    private final String validationDelimiter;

    public TestDaoCsv(String sourcePath, String colDelimiter, String answersDelimiter, String validationDelimiter) {

        this.sourcePath = sourcePath;
        this.colDelimiter = colDelimiter;
        this.answersDelimiter = answersDelimiter;
        this.validationDelimiter = validationDelimiter;
    }

    @Override
    public SimpleTest loadTest() throws TestReadingException {
        List<Question> questions = new ArrayList<>();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(sourcePath)) {

            if (is == null) {
                throw new TestReadingException("File with test not found");
            }
            InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);

            try (Scanner sc = new Scanner(reader)) {
                while (sc.hasNextLine()) {
                    questions.add(getQuestionFromString(sc.nextLine()));
                }
            }
        } catch (IOException | IndexOutOfBoundsException e) {
            throw new TestReadingException(e);
        }
        return new SimpleTest(questions);
    }

    private Question getQuestionFromString(String line) {
        String[] cols = line.split(colDelimiter);
        String questionText = cols[0];
        List<Answer> answers = Arrays.stream(cols[1].split(answersDelimiter))
                .map(this::getAnswerFromString)
                .toList();

        return new Question(questionText, answers);
    }

    private Answer getAnswerFromString(String answerString) {
        String[] answerParts = answerString.split(validationDelimiter);
        return new Answer(answerParts[0], Boolean.valueOf(answerParts[1]), Integer.parseInt(answerParts[2]));
    }
}
