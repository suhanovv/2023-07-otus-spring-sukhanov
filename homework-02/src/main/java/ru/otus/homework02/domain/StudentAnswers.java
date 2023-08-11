package ru.otus.homework02.domain;

import java.util.List;

public class StudentAnswers {
    private final Student student;

    private final List<Answer> answers;

    public StudentAnswers(Student student,List<Answer> answers) {
        this.student = student;
        this.answers = answers;
    }

    public Student getStudent() {
        return student;
    }

    public List<Answer> getAnswers() {
        return answers;
    }
}
