package ru.otus.homework01.dao;

import io.vavr.control.Either;
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
    public Either<String, SimpleTest> loadTest() {
        List<Question> questions = new ArrayList<>();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(sourcePath)) {

            if (is == null) {
                return Either.left("Не найден файл с тестом");
            }
            InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);

            try (Scanner sc = new Scanner(reader)) {
                while (sc.hasNextLine()) {
                    questions.add(mapQuestionFromLine(sc.nextLine()));
                }
            }
        } catch (IOException e) {
            return Either.left("Ошибка загрузки файла с тестом");
        } catch (IndexOutOfBoundsException e) {
            return Either.left("Некорректный формат файла");
        }

        return Either.right(new SimpleTest(questions));
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
