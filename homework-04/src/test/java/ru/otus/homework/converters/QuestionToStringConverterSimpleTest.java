package ru.otus.homework.converters;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class QuestionToStringConverterSimpleTest {

    @Autowired
    QuestionToStringConverter converter;

    @Test
    void testConvertShouldReturnValidString() {
        List<Answer> answers = List.of(
                new Answer("Answer 1", true, 1),
                new Answer("Answer 2", false, 2),
                new Answer("Answer 3", false, 3),
                new Answer("Answer 4", false, 4));

        Question question = new Question("Question 1", answers);

        String newLine = System.getProperty("line.separator");
        String expected = "Question 1" + newLine
                + "    1) Answer 1" + newLine
                + "    2) Answer 2" + newLine
                + "    3) Answer 3" + newLine
                + "    4) Answer 4";

        assertEquals(expected, converter.convert(question));
    }
}