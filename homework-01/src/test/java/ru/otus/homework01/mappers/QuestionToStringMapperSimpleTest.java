package ru.otus.homework01.mappers;

import org.junit.jupiter.api.Test;
import ru.otus.homework01.domain.Answer;
import ru.otus.homework01.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionToStringMapperSimpleTest {

    @Test
    void testMapShouldReturnValidString() {
        List<Answer> answers = List.of(
                new Answer("Answer 1"),
                new Answer("Answer 2"),
                new Answer("Answer 3"),
                new Answer("Answer 4"));

        Question question = new Question("Question 1", answers, new Answer("Answer 1"));
        String expected = """
                   Question 1
                       1) Answer 1
                       2) Answer 2
                       3) Answer 3
                       4) Answer 4""";

        assertEquals(expected, QuestionToStringMapper.map(question));
    }
}