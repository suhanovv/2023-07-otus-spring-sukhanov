package ru.otus.homework01.mappers;

import org.junit.jupiter.api.Test;
import ru.otus.homework01.domain.Answer;
import ru.otus.homework01.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionToStringMapperSimpleTest {

    @Test
    void testMapShouldReturnValidString() {
        QuestionToStringMapper mapper = new QuestionToStringMapper();
        List<Answer> answers = List.of(
                new Answer("Answer 1", true),
                new Answer("Answer 2", false),
                new Answer("Answer 3", false),
                new Answer("Answer 4", false));

        Question question = new Question("Question 1", answers);

        String newLine = System.getProperty("line.separator");
        String expected = "Question 1" + newLine
                + "    1) Answer 1" + newLine
                + "    2) Answer 2" + newLine
                + "    3) Answer 3" + newLine
                + "    4) Answer 4";

        assertEquals(expected, mapper.map(question));
    }
}