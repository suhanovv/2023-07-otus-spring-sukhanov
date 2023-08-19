package ru.otus.homework.services.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.SimpleTest;
import ru.otus.homework.domain.Student;
import ru.otus.homework.domain.TestResult;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    private final QuestionService questionService;

    private final TestResultCalculatorService testResultCalculatorService;

    @Autowired
    public TestServiceImpl(
            QuestionService questionService,
            TestResultCalculatorService testResultCalculatorService) {
        this.questionService = questionService;
        this.testResultCalculatorService = testResultCalculatorService;
    }

    @Override
    public TestResult executeTest(SimpleTest test, Student student) {
        List<Answer> answers = new ArrayList<>();

        for (Question question : test.getQuestions()) {
            Answer answer = questionService.getUserAnswer(question);
            answers.add(answer);
        }

        return testResultCalculatorService.getTestResult(student, answers);
    }
}
