package ru.otus.homework.services.test;

import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Student;
import ru.otus.homework.domain.TestResult;

import java.util.List;


public interface TestResultCalculatorService {
    TestResult getTestResult(Student student, List<Answer> studentAnswers);
}
