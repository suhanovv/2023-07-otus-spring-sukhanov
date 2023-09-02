package ru.otus.homework.services.test;

import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;

public interface QuestionService {
    Answer getUserAnswer(Question question);
}
