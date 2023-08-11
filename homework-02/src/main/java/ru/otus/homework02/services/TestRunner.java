package ru.otus.homework02.services;

import ru.otus.homework02.dao.exceptions.TestReadingException;
import ru.otus.homework02.domain.SimpleTest;
import ru.otus.homework02.domain.Student;
import ru.otus.homework02.domain.TestResult;
import ru.otus.homework02.mappers.TestResultMapper;
import ru.otus.homework02.services.io.IOService;
import ru.otus.homework02.services.student.StudentRequestInfoService;
import ru.otus.homework02.services.test.TestService;

public class TestRunner {
    private final TestService testService;

    private final IOService ioService;

    private final StudentRequestInfoService studentRequestService;

    private final TestResultMapper testResultMapper;

    public TestRunner(
            TestService testService,
            IOService ioService,
            StudentRequestInfoService studentRequestService,
            TestResultMapper testResultMapper) {
        this.testService = testService;
        this.ioService = ioService;
        this.studentRequestService = studentRequestService;
        this.testResultMapper = testResultMapper;
    }

    public void runTest() {
        try {
            SimpleTest test = testService.loadTest();
            Student student = studentRequestService.getStudent();
            TestResult result = testService.executeTest(test, student);
            ioService.outputString(testResultMapper.mapToString(result));
        } catch (TestReadingException e) {
            ioService.outputString("Test loading error: " + e.getMessage());
        }
    }


}
