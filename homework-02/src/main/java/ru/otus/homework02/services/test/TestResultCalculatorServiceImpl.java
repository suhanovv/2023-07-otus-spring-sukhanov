package ru.otus.homework02.services.test;

import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.StudentAnswers;
import ru.otus.homework02.domain.TestResult;

public class TestResultCalculatorServiceImpl implements TestResultCalculatorService {
    private final int successBaselinePercent;

    public TestResultCalculatorServiceImpl(int successBaselinePercent) {
        this.successBaselinePercent = successBaselinePercent;
    }

    @Override
    public TestResult getTestResult(StudentAnswers answers) {
        int totalAnswersCount = answers.getAnswers().size();
        long validAnswersCount = answers.getAnswers().stream().filter(Answer::getValid).count();
        int validAnswersPercent = (int) ((validAnswersCount / (double) totalAnswersCount) * 100);

        return new TestResult(answers.getStudent(), validAnswersPercent >= successBaselinePercent, validAnswersPercent);
    }
}
