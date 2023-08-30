package ru.otus.homework.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.homework.config.DaoConfig;
import ru.otus.homework.dao.exceptions.TestReadingException;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.SimpleTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class TestDaoCsvTest {

    @MockBean
    private DaoConfig configMock;

    @MockBean
    private TestFileNameProvider fileNameProvider;

    @Autowired
    private TestDao dao;

    @Test
    void loadTest() throws TestReadingException {
        SimpleTest expectedTest = new SimpleTest(List.of(
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
        when(configMock.getColDelimiter()).thenReturn(",");
        when(configMock.getAnswersDelimiter()).thenReturn(";");
        when(configMock.getValidationDelimiter()).thenReturn("::");
        when(fileNameProvider.getFilename()).thenReturn("questions/en.csv");

        SimpleTest test = dao.loadTest();

        assertThat(test).usingRecursiveComparison().isEqualTo(expectedTest);
    }
}