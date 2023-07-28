package ru.otus.homework01.dao;

import ru.otus.homework01.domain.Question;
import ru.otus.homework01.domain.Test;

import java.io.BufferedReader;
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
    public Test loadTest() {
        List<Question> questions = new ArrayList<>();

        InputStream is = getClass().getClassLoader().getResourceAsStream(sourcePath);

        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);

        try (Scanner sc = new Scanner(reader)) {
            while (sc.hasNextLine()) {
                questions.add(getQuestionFromLine(sc.nextLine()));
            }
        }
        return new Test(questions);
    }

    private Question getQuestionFromLine(String line) {
        String[] cols = line.split(colDelimeter);
        return new Question(cols[0], Arrays.asList(cols[1].split(answersDelimeter)), cols[2]);
    }
}
