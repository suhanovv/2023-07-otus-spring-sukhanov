package ru.otus.homework02.domain;

import lombok.Getter;

@Getter
public class Answer {
    private final String answerText;

    private final boolean isValid;

    private final int id;


    public Answer(String answerText, Boolean isValid, int id) {
        this.answerText = answerText;
        this.isValid = isValid;
        this.id = id;
    }
}
