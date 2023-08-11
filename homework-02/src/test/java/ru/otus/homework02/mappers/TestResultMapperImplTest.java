package ru.otus.homework02.mappers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.otus.homework02.domain.Student;
import ru.otus.homework02.domain.TestResult;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TestResultMapperImplTest {

    @ParameterizedTest
    @MethodSource("argSource")
    void mapToStringShouldReturnValidString(String expectedString, TestResult result) {
        TestResultMapper testResultMapper = new TestResultMapperImpl();

        String actualString = testResultMapper.mapToString(result);

        assertEquals(expectedString, actualString);
    }

    private static Stream<Arguments> argSource() {
        Student student = new Student("Test", "Testovich");
        String newLine = System.getProperty("line.separator");
        return Stream.of(
                Arguments.arguments(

                                "Student: Testovich Test" + newLine
                                + "Test passed with score: 50%",
                        new TestResult(student, true, 50)

                ),
                Arguments.arguments(
                        "Student: Testovich Test" + newLine
                        + "Test failed with score: 50%",
                        new TestResult(student, false, 50)
                )
        );
    }
}