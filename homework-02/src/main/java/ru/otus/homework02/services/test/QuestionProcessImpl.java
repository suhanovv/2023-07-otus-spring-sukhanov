package ru.otus.homework02.services.test;

import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.Question;
import ru.otus.homework02.mappers.QuestionMapper;
import ru.otus.homework02.services.io.IOService;

import java.util.Optional;

public class QuestionProcessImpl implements QuestionProcess {
    private final IOService ioService;

    private final QuestionMapper questionMapper;

    public QuestionProcessImpl(IOService ioService,QuestionMapper questionMapper) {
        this.ioService = ioService;
        this.questionMapper = questionMapper;
    }

    @Override
    public Answer getUserAnswer(Question question) {
        ioService.outputStringWithNewline(questionMapper.mapToString(question));
        while (true) {
            int answerNumber = ioService.readIntWithPrompt("Your answer is: ");
            Optional<Answer> userAnswer = question.getAnswers().stream()
                    .filter(i -> i.getNumber() == answerNumber)
                    .findFirst();
            if (userAnswer.isPresent()) {
                return userAnswer.get();
            } else {
                ioService.outputStringWithNewline("Invalid answer, try again");
            }
        }
    }
}
