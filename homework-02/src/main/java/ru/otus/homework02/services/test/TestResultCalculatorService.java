package ru.otus.homework02.services.test;

import ru.otus.homework02.domain.StudentAnswers;
import ru.otus.homework02.domain.TestResult;


public interface TestResultCalculatorService {
    TestResult getTestResult(StudentAnswers answers);
}
