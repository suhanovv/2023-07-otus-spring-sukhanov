package ru.otus.homework01.domain;

public class Answer {
    private final String answerText;

    private final Boolean isValid;

    public Answer(String answerText, Boolean isValid) {
        this.answerText = answerText;
        this.isValid = isValid;
    }

    public String getAnswerText() {
        return answerText;
    }

    public Boolean getValid() {
        return isValid;
    }
}
