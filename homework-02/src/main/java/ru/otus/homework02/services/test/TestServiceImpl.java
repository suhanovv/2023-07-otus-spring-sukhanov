package ru.otus.homework02.services.test;

import ru.otus.homework02.dao.TestDao;
import ru.otus.homework02.dao.exceptions.TestReadingException;
import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.Question;
import ru.otus.homework02.domain.SimpleTest;
import ru.otus.homework02.domain.Student;
import ru.otus.homework02.domain.StudentAnswers;
import ru.otus.homework02.domain.TestResult;

import java.util.ArrayList;
import java.util.List;

public class TestServiceImpl implements TestService {

    private final TestDao dao;

    private final QuestionProcess questionProcess;

    private final TestResultCalculatorService testResultCalculatorService;

    public TestServiceImpl(
            TestDao dao,
            QuestionProcess questionProcess,
            TestResultCalculatorService testResultCalculatorService) {
        this.dao = dao;
        this.questionProcess = questionProcess;
        this.testResultCalculatorService = testResultCalculatorService;
    }

    @Override
    public SimpleTest loadTest() throws TestReadingException {
        return dao.loadTest();
    }

    @Override
    public TestResult executeTest(SimpleTest test, Student student) {
        List<Answer> answers = new ArrayList<>();

        for (Question question : test.getQuestions()) {
            Answer answer = questionProcess.getUserAnswer(question);
            answers.add(answer);
        }

        StudentAnswers studentAnswers = new StudentAnswers(student, answers);
        return testResultCalculatorService.getTestResult(studentAnswers);
    }
}
