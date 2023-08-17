package ru.otus.homework02.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class SimpleTest {
    private final List<Question> questions;

    public SimpleTest(List<Question> questions) {
        this.questions = questions;
    }

}
