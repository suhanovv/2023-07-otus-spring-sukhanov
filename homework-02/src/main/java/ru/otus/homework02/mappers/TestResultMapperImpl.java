package ru.otus.homework02.mappers;

import ru.otus.homework02.domain.Student;
import ru.otus.homework02.domain.TestResult;

import java.util.ArrayList;
import java.util.List;

public class TestResultMapperImpl implements TestResultMapper {
    @Override
    public String mapToString(TestResult result) {
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
