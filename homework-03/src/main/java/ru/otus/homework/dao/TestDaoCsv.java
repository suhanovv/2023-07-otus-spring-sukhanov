package ru.otus.homework.dao;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.homework.config.DaoConfig;
import ru.otus.homework.dao.exceptions.TestReadingException;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.SimpleTest;

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
@AllArgsConstructor
public class TestDaoCsv implements TestDao {

    private final DaoConfig daoConfig;

    private final TestFileNameProvider fileNameProvider;

    @Override
    public SimpleTest loadTest() throws TestReadingException {
        List<Question> questions = new ArrayList<>();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(
                fileNameProvider.getFilename())) {

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
