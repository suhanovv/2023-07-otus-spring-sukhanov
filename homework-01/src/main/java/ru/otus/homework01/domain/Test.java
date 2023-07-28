package ru.otus.homework01.domain;

import java.util.List;

public class Test {
    private final List<Question> questions;

    public Test(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
