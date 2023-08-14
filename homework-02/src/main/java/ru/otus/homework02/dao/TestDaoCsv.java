package ru.otus.homework02.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.otus.homework02.config.DaoConfig;
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

@Repository
public class TestDaoCsv implements TestDao {

    private final DaoConfig daoConfig;

    @Autowired
    public TestDaoCsv(DaoConfig daoConfig) {

        this.daoConfig = daoConfig;
    }

    @Override
    public SimpleTest loadTest() throws TestReadingException {
        List<Question> questions = new ArrayList<>();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(daoConfig.getSourcePath())) {

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
        String[] cols = line.split(daoConfig.getColDelimiter());
        String questionText = cols[0];
        List<Answer> answers = Arrays.stream(cols[1].split(daoConfig.getAnswersDelimiter()))
                .map(this::getAnswerFromString)
                .toList();

        return new Question(questionText, answers);
    }

    private Answer getAnswerFromString(String answerString) {
        String[] answerParts = answerString.split(daoConfig.getValidationDelimiter());
        return new Answer(answerParts[0], Boolean.valueOf(answerParts[1]), Integer.parseInt(answerParts[2]));
    }
}
