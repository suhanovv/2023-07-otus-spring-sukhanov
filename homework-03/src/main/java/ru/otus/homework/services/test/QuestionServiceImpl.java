package ru.otus.homework.services.test;

import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;
import ru.otus.homework.services.io.IOService;
import ru.otus.homework.services.localization.LocalizationService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final IOService ioService;

    private final ConversionService conversionService;

    private final LocalizationService localizationService;


    @Override
    public Answer getUserAnswer(Question question) {
        ioService.outputStringWithNewline(conversionService.convert(question, String.class));
        while (true) {
            int answerNumber = ioService.readIntWithPrompt(
                    localizationService.getMessage("answer.prompt") + " ");
            Optional<Answer> userAnswer = question.getAnswers().stream()
                    .filter(i -> i.getId() == answerNumber)
                    .findFirst();
            if (userAnswer.isPresent()) {
                return userAnswer.get();
            } else {
                ioService.outputStringWithNewline(localizationService.getMessage("answer.incorrect"));
            }
        }
    }
}
