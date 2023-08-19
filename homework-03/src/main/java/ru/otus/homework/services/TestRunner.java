package ru.otus.homework.services;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.otus.homework.dao.exceptions.TestReadingException;
import ru.otus.homework.domain.SimpleTest;
import ru.otus.homework.domain.Student;
import ru.otus.homework.domain.TestResult;
import ru.otus.homework.services.io.IOService;
import ru.otus.homework.services.student.StudentRequestInfoService;
import ru.otus.homework.services.test.DisplayTestResultService;
import ru.otus.homework.services.localization.LocalizationService;
import ru.otus.homework.services.test.TestLoaderService;
import ru.otus.homework.services.test.TestService;

@Component
@AllArgsConstructor
public class TestRunner implements ApplicationRunner {
    private final TestService testService;

    private final TestLoaderService testLoader;

    private final IOService ioService;

    private final StudentRequestInfoService studentRequestService;

    private final DisplayTestResultService displayTestResultService;

    private final LocalizationService localizationService;

    @Override
    public void run(ApplicationArguments args) {
        try {
            SimpleTest test = testLoader.load();
            Student student = studentRequestService.getStudent();
            TestResult result = testService.executeTest(test, student);
            displayTestResultService.display(result);
        } catch (TestReadingException e) {
            ioService.outputString(localizationService.getMessage("test.load.error"));
        }
    }
}
