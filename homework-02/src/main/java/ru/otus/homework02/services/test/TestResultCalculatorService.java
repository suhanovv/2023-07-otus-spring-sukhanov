package ru.otus.homework02.services.test;

import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.Student;
import ru.otus.homework02.domain.TestResult;

import java.util.List;


public interface TestResultCalculatorService {
    TestResult getTestResult(Student student, List<Answer> studentAnswers);
}
