package ru.otus.homework02.services.test;

import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.Question;

public interface QuestionProcess {
    Answer getUserAnswer(Question question);
}
