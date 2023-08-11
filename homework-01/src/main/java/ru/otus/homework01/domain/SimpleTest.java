package ru.otus.homework01.domain;

import java.util.List;

public class SimpleTest {
    private final List<Question> questions;

    public SimpleTest(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
