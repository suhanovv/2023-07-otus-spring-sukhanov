package ru.otus.homework02.services.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.Question;
import ru.otus.homework02.services.io.IOService;

import java.util.Optional;

@Service
public class QuestionProcessImpl implements QuestionProcess {
    private final IOService ioService;

    private final ConversionService conversionService;

    @Autowired
    public QuestionProcessImpl(IOService ioService, ConversionService conversionService) {
        this.ioService = ioService;
        this.conversionService = conversionService;
    }

    @Override
    public Answer getUserAnswer(Question question) {
        ioService.outputStringWithNewline(conversionService.convert(question, String.class));
        while (true) {
            int answerNumber = ioService.readIntWithPrompt("Your answer is: ");
            Optional<Answer> userAnswer = question.getAnswers().stream()
                    .filter(i -> i.getId() == answerNumber)
                    .findFirst();
            if (userAnswer.isPresent()) {
                return userAnswer.get();
            } else {
                ioService.outputStringWithNewline("Invalid answer, try again");
            }
        }
    }
}
