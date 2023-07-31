package ru.otus.homework01.domain;


import java.util.List;

public class Question {
    private final String questionText;

    private final List<Answer> answers;

    private final Answer validAnswer;

    public Question(String questionText, List<Answer> answers, Answer validAnswer) {
        this.questionText = questionText;
        this.answers = answers;
        this.validAnswer = validAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public Answer getValidAnswer() {
        return validAnswer;
    }
}
