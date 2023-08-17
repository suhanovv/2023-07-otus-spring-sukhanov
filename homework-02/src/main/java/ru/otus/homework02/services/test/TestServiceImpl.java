package ru.otus.homework02.services.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.Question;
import ru.otus.homework02.domain.SimpleTest;
import ru.otus.homework02.domain.Student;
import ru.otus.homework02.domain.TestResult;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    private final QuestionProcess questionProcess;

    private final TestResultCalculatorService testResultCalculatorService;

    @Autowired
    public TestServiceImpl(
            QuestionProcess questionProcess,
            TestResultCalculatorService testResultCalculatorService) {
        this.questionProcess = questionProcess;
        this.testResultCalculatorService = testResultCalculatorService;
    }

    @Override
    public TestResult executeTest(SimpleTest test, Student student) {
        List<Answer> answers = new ArrayList<>();

        for (Question question : test.getQuestions()) {
            Answer answer = questionProcess.getUserAnswer(question);
            answers.add(answer);
        }

        return testResultCalculatorService.getTestResult(student, answers);
    }
}
