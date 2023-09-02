package ru.otus.homework.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.homework.dao.exceptions.TestReadingException;
import ru.otus.homework.domain.SimpleTest;
import ru.otus.homework.domain.Student;
import ru.otus.homework.domain.TestResult;
import ru.otus.homework.services.io.IOService;
import ru.otus.homework.services.localization.LocalizationService;
import ru.otus.homework.services.test.DisplayTestResultService;
import ru.otus.homework.services.test.TestLoaderService;
import ru.otus.homework.services.test.TestService;

@Component
@AllArgsConstructor
public class TestRunner {
    private final TestService testService;

    private final TestLoaderService testLoader;

    private final IOService ioService;

    private final DisplayTestResultService displayTestResultService;

    private final LocalizationService localizationService;

    public void runForStudent(Student student) {
        try {
            SimpleTest test = testLoader.load();
            TestResult result = testService.executeTest(test, student);
            displayTestResultService.display(result);
        } catch (TestReadingException e) {
            ioService.outputString(localizationService.getMessage("test.load.error"));
        }
    }
}
