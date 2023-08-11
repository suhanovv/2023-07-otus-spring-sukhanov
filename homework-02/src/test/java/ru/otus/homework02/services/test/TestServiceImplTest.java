package ru.otus.homework02.services.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework02.dao.TestDao;
import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.Question;
import ru.otus.homework02.domain.SimpleTest;
import ru.otus.homework02.domain.Student;
import ru.otus.homework02.domain.StudentAnswers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {

    @Mock
    private TestDao daoMock;

    @Mock
    private QuestionProcess questionProcessMock;

    @Mock
    private TestResultCalculatorService testResultCalculatorMock;

    @Test
    void executeTestCheckCalculatorServiceCalledWithValidArg() {
        Student student = new Student("Ivan", "Ivanov");
        SimpleTest test = new SimpleTest(List.of(
                new Question("How much is 1+1?", List.of(
                        new Answer("0", false, 1),
                        new Answer("1", false, 2),
                        new Answer("2", true, 3),
                        new Answer("4", false, 4)
                )),
                new Question("How much is 2+2?", List.of(
                        new Answer("1", false, 1),
                        new Answer("2", false, 2),
                        new Answer("3", false, 3),
                        new Answer("4", true, 4)
                ))

        ));
        when(questionProcessMock.getUserAnswer(any(Question.class)))
                .thenReturn(test.getQuestions().get(0).getAnswers().get(2))
                .thenReturn(test.getQuestions().get(0).getAnswers().get(3));

        TestService testService = new TestServiceImpl(daoMock, questionProcessMock, testResultCalculatorMock);
        testService.executeTest(test, student);

        verify(testResultCalculatorMock)
                .getTestResult(argThat(x -> {
                    assertThat(x)
                        .usingRecursiveComparison()
                        .isEqualTo(new StudentAnswers(student, List.of(
                            test.getQuestions().get(0).getAnswers().get(2),
                            test.getQuestions().get(0).getAnswers().get(3)
                        )));
                    return true;
                }
                ));
    }
}