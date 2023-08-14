package ru.otus.homework02.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import ru.otus.homework02.dao.exceptions.TestReadingException;
import ru.otus.homework02.domain.SimpleTest;
import ru.otus.homework02.domain.Student;
import ru.otus.homework02.domain.TestResult;
import ru.otus.homework02.services.io.IOService;
import ru.otus.homework02.services.student.StudentRequestInfoService;
import ru.otus.homework02.services.test.TestLoaderService;
import ru.otus.homework02.services.test.TestService;

@Component
public class TestRunner {
    private final TestService testService;

    private final TestLoaderService testLoader;

    private final IOService ioService;

    private final StudentRequestInfoService studentRequestService;

    private final ConversionService conversionService;

    @Autowired
    public TestRunner(
            TestService testService,
            TestLoaderService testLoader, IOService ioService,
            StudentRequestInfoService studentRequestService,
            ConversionService conversionService) {
        this.testService = testService;
        this.testLoader = testLoader;
        this.ioService = ioService;
        this.studentRequestService = studentRequestService;
        this.conversionService = conversionService;
    }

    public void runTest() {
        try {
            SimpleTest test = testLoader.load();
            Student student = studentRequestService.getStudent();
            TestResult result = testService.executeTest(test, student);
            ioService.outputString(conversionService.convert(result, String.class));
        } catch (TestReadingException e) {
            ioService.outputString("Test loading error: " + e.getMessage());
        }
    }


}
