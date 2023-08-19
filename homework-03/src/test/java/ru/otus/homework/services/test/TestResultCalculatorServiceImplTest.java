package ru.otus.homework.services.test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.config.TestResultCalculatorConfig;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Student;
import ru.otus.homework.domain.TestResult;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestResultCalculatorServiceImplTest {

    @Mock
    private TestResultCalculatorConfig configMock;

    @ParameterizedTest
    @MethodSource("studentAnswers")
    void getTestResult(Student student, TestResult expectedResult, List<Answer> answers) {
        when(configMock.getSuccessBaselinePercent()).thenReturn(50);
        TestResultCalculatorService testResultCalculatorService = new TestResultCalculatorServiceImpl(configMock);

        TestResult actualResult = testResultCalculatorService.getTestResult(student, answers);

        assertThat(actualResult).usingRecursiveComparison().isEqualTo(expectedResult);

    }

    private static Stream<Arguments> studentAnswers() {
        Student student = new Student("Ivan", "Ivanov");

        return Stream.of(
                Arguments.arguments(
                        student,
                        new TestResult(student, true, 50,
                        List.of(
                               new Answer("Test 1", true, 1),
                                new Answer("Test 2", false, 1)
                        )),
                        List.of(
                                new Answer("Test 1", true, 1),
                                new Answer("Test 2", false, 1)
                        )
                ), Arguments.arguments(
                        student,
                        new TestResult(student, false, 0,
                                List.of(
                                new Answer("Test 1", false, 1),
                                new Answer("Test 2", false, 1)
                        )),
                        List.of(
                                new Answer("Test 1", false, 1),
                                new Answer("Test 2", false, 1)
                        )
                ), Arguments.arguments(
                        student,
                        new TestResult(student, true, 100,
                                List.of(
                                new Answer("Test 1", true, 1),
                                new Answer("Test 2", true, 1)
                        )
                ),
                        List.of(
                                new Answer("Test 1", true, 1),
                                new Answer("Test 2", true, 1)
                        ))

        );
    }
}