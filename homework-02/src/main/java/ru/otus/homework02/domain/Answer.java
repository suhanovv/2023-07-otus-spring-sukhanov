package ru.otus.homework02.domain;

public class Answer {
    private final String answerText;

    private final boolean isValid;

    private final int number;


    public Answer(String answerText, Boolean isValid, int number) {
        this.answerText = answerText;
        this.isValid = isValid;
        this.number = number;
    }

    public String getAnswerText() {
        return answerText;
    }

    public Boolean getValid() {
        return isValid;
    }

    public int getNumber() {
        return number;
    }
}
