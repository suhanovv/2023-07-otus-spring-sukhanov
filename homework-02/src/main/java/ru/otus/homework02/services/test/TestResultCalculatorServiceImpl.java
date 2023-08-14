package ru.otus.homework02.services.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework02.config.TestResultCalculatorConfig;
import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.Student;
import ru.otus.homework02.domain.TestResult;

import java.util.List;

@Service
public class TestResultCalculatorServiceImpl implements TestResultCalculatorService {
    private final TestResultCalculatorConfig config;

    @Autowired
    public TestResultCalculatorServiceImpl(TestResultCalculatorConfig config) {
        this.config = config;
    }

    @Override
    public TestResult getTestResult(Student student, List<Answer> studentAnswers) {
        int totalAnswersCount = studentAnswers.size();
        long validAnswersCount = studentAnswers.stream().filter(Answer::isValid).count();
        int validAnswersPercent = (int) ((validAnswersCount / (double) totalAnswersCount) * 100);

        return new TestResult(
                student,
                validAnswersPercent >= config.getSuccessBaselinePercent(),
                validAnswersPercent,
                studentAnswers);
    }
}
