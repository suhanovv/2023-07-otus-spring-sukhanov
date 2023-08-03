package ru.otus.homework01.dao;

import ru.otus.homework01.dao.exceptions.TestReadingException;
import ru.otus.homework01.domain.Answer;
import ru.otus.homework01.domain.Question;
import ru.otus.homework01.domain.SimpleTest;

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

    private final String colDelimeter;

    private final String answersDelimeter;

    public TestDaoCsv(String sourcePath, String colDelimeter, String answersDelimeter) {

        this.sourcePath = sourcePath;
        this.colDelimeter = colDelimeter;
        this.answersDelimeter = answersDelimeter;
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
                    questions.add(mapQuestionFromLine(sc.nextLine()));
                }
            }
        } catch (IOException | IndexOutOfBoundsException e) {
            throw new TestReadingException(e);
        }
        return new SimpleTest(questions);
    }

    private Question mapQuestionFromLine(String line) {
        String[] cols = line.split(colDelimeter);
        String questionText = cols[0];
        List<Answer> answers = Arrays.stream(cols[1].split(answersDelimeter))
                .map(Answer::new)
                .toList();
        Answer validAnswer = new Answer(cols[2]);

        return new Question(questionText, answers, validAnswer);
    }
}
