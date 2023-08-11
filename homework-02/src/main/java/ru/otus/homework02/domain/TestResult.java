package ru.otus.homework02.domain;

public class TestResult {
    private final boolean isSuccess;

    private final int validAnswersPercent;

    private final Student student;

    public TestResult(Student student, boolean isSuccess, int validAnswersPercent) {
        this.isSuccess = isSuccess;
        this.validAnswersPercent = validAnswersPercent;
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public int getValidAnswersPercent() {
        return validAnswersPercent;
    }
}
