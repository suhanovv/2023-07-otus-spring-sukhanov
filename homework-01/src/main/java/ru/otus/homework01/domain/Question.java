package ru.otus.homework01.domain;


import java.util.List;

public class Question {
    private final String questionText;

    private final List<String> answers;

    private final String validAnswer;

    public Question(String questionText, List<String> answers, String validAnswer) {
        this.questionText = questionText;
        this.answers = answers;
        this.validAnswer = validAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public String getValidAnswer() {
        return validAnswer;
    }
}
