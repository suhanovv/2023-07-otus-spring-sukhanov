package ru.otus.homework02.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class TestResult {
    private final boolean isSuccess;

    private final int validAnswersPercent;

    private final Student student;

    private final List<Answer> studentAnswers;

    public TestResult(Student student, boolean isSuccess, int validAnswersPercent, List<Answer> studentAnswers) {
        this.isSuccess = isSuccess;
        this.validAnswersPercent = validAnswersPercent;
        this.student = student;
        this.studentAnswers = studentAnswers;
    }

}
