package ru.otus.homework02.services.test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.Student;
import ru.otus.homework02.domain.StudentAnswers;
import ru.otus.homework02.domain.TestResult;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class TestResultCalculatorServiceImplTest {

    @ParameterizedTest
    @MethodSource("studentAnswers")
    void getTestResult(TestResult expectedResult, StudentAnswers answers) {
        TestResultCalculatorService testResultCalculatorService = new TestResultCalculatorServiceImpl(50);

        TestResult actualResult = testResultCalculatorService.getTestResult(answers);

        assertThat(actualResult).usingRecursiveComparison().isEqualTo(expectedResult);

    }

    private static Stream<Arguments> studentAnswers() {
        Student student = new Student("Ivan", "Ivanov");
        return Stream.of(
                Arguments.arguments(
                        new TestResult(student, true, 50),
                        new StudentAnswers(student, List.of(
                               new Answer("Test 1", true, 1),
                                new Answer("Test 2", false, 1)
                        ))
                ), Arguments.arguments(
                        new TestResult(student, false, 0),
                        new StudentAnswers(student, List.of(
                                new Answer("Test 1", false, 1),
                                new Answer("Test 2", false, 1)
                        ))
                ), Arguments.arguments(
                        new TestResult(student, true, 100),
                        new StudentAnswers(student, List.of(
                                new Answer("Test 1", true, 1),
                                new Answer("Test 2", true, 1)
                        ))
                )
        );
    }
}