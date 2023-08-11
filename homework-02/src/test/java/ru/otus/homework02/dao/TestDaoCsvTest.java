package ru.otus.homework02.dao;

import org.junit.jupiter.api.Test;
import ru.otus.homework02.dao.exceptions.TestReadingException;
import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.Question;
import ru.otus.homework02.domain.SimpleTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TestDaoCsvTest {

    @Test
    void loadTest() throws TestReadingException {
        SimpleTest expectedTest = new SimpleTest(List.of(
                new Question("How much is 1+1?", List.of(
                        new Answer("0", false, 1),
                        new Answer("1", false, 2),
                        new Answer("2", true, 3),
                        new Answer("4", false, 4)
                )),
                new Question("How much is 2+2?", List.of(
                        new Answer("1", false, 1),
                        new Answer("2", false, 2),
                        new Answer("3", false, 3),
                        new Answer("4", true, 4)
                ))

        ));

        TestDao dao = new TestDaoCsv("testQuestions.csv", ",", ";", "::");
        SimpleTest test = dao.loadTest();

        assertThat(test).usingRecursiveComparison().isEqualTo(expectedTest);
    }
}