package ru.otus.homework02.converters;

import org.springframework.core.convert.converter.Converter;
import ru.otus.homework02.domain.Student;
import ru.otus.homework02.domain.TestResult;

import java.util.ArrayList;
import java.util.List;

public class TestResultToStringConverter implements Converter<TestResult, String> {
    @Override
    public String convert(TestResult result) {
        List<String> resultLines = new ArrayList<>();
        String newLine = System.getProperty("line.separator");
        Student student = result.getStudent();
        resultLines.add("Student: " + student.getLastName() + " " + student.getFirstName());
        resultLines.add("Test "
                + (result.isSuccess() ? "passed" : "failed")
                + " with score: " + result.getValidAnswersPercent() + "%");

        return String.join(newLine, resultLines);
    }
}
