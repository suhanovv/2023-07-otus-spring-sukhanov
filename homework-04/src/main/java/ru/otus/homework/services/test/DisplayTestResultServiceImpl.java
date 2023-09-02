package ru.otus.homework.services.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Student;
import ru.otus.homework.domain.TestResult;
import ru.otus.homework.services.io.IOService;
import ru.otus.homework.services.localization.LocalizationService;

@Service
public class DisplayTestResultServiceImpl implements DisplayTestResultService {

    private final IOService ioService;

    private final LocalizationService localizationService;

    @Autowired
    public DisplayTestResultServiceImpl(IOService ioService, LocalizationService localizationService) {
        this.ioService = ioService;
        this.localizationService = localizationService;
    }

    @Override
    public void display(TestResult result) {
        Student student = result.getStudent();
        ioService.outputStringWithNewline(
                localizationService.getMessage("test.result.student", student.getFirstName(), student.getLastName()));

        String resultString = localizationService.getMessage(
                result.isSuccess() ? "test.result.passed" : "test.result.failed");

        ioService.outputStringWithNewline(
                localizationService.getMessage(
                        "test.result.result-string", resultString, result.getValidAnswersPercent())
        );

    }
}
