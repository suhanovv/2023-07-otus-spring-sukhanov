package ru.otus.homework02.converters;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.otus.homework02.domain.Student;
import ru.otus.homework02.domain.TestResult;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TestResultToStringConverterTest {

    @ParameterizedTest
    @MethodSource("argSource")
    void mapToStringShouldReturnValidString(String expectedString, TestResult result) {
        TestResultToStringConverter testResultConverter = new TestResultToStringConverter();

        String actualString = testResultConverter.convert(result);

        assertEquals(expectedString, actualString);
    }

    private static Stream<Arguments> argSource() {
        Student student = new Student("Test", "Testovich");
        String newLine = System.getProperty("line.separator");
        return Stream.of(
                Arguments.arguments(

                                "Student: Testovich Test" + newLine
                                + "Test passed with score: 50%",
                        new TestResult(student, true, 50, new ArrayList<>())

                ),
                Arguments.arguments(
                        "Student: Testovich Test" + newLine
                        + "Test failed with score: 50%",
                        new TestResult(student, false, 50, new ArrayList<>())
                )
        );
    }
}