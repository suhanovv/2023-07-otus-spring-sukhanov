package ru.otus.homework01.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework01.dao.TestDao;
import ru.otus.homework01.dao.exceptions.TestReadingException;
import ru.otus.homework01.domain.Answer;
import ru.otus.homework01.domain.Question;
import ru.otus.homework01.domain.SimpleTest;

import java.util.List;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TestServiceImplSimpleTest {

    @Mock
    private TestDao daoMock;

    @Test
    void testRunTestCheckAllQuestionDisplayed(@Mock PresenterService presenterMock) throws TestReadingException {
        SimpleTest test = new SimpleTest(List.of(
                new Question("Question 1", List.of(
                        new Answer("Answer 1"),
                        new Answer("Answer 2"),
                        new Answer("Answer 3"),
                        new Answer("Answer 4")),
                        new Answer("Answer 1")),
                new Question("Question 2", List.of(
                        new Answer("Answer 5"),
                        new Answer("Answer 6"),
                        new Answer("Answer 7"),
                        new Answer("Answer 8")),
                            new Answer("Answer 5")
                )));
        when(daoMock.loadTest()).thenReturn(test);

        TestService service = new TestServiceImpl(daoMock, presenterMock);

        service.runTest();

        verify(presenterMock, times(2)).display(anyString());

    }
}