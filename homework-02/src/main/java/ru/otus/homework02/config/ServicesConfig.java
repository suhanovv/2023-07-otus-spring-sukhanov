package ru.otus.homework02.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import ru.otus.homework02.dao.TestDao;
import ru.otus.homework02.mappers.QuestionMapper;
import ru.otus.homework02.mappers.QuestionMapperImpl;
import ru.otus.homework02.mappers.TestResultMapper;
import ru.otus.homework02.mappers.TestResultMapperImpl;

import ru.otus.homework02.services.TestRunner;
import ru.otus.homework02.services.io.IOService;
import ru.otus.homework02.services.io.IOServiceImpl;

import ru.otus.homework02.services.student.StudentRequestInfoService;
import ru.otus.homework02.services.student.StudentRequestInfoServiceImpl;
import ru.otus.homework02.services.test.QuestionProcess;
import ru.otus.homework02.services.test.QuestionProcessImpl;
import ru.otus.homework02.services.test.TestResultCalculatorService;
import ru.otus.homework02.services.test.TestResultCalculatorServiceImpl;
import ru.otus.homework02.services.test.TestService;
import ru.otus.homework02.services.test.TestServiceImpl;


@Configuration
@PropertySource("classpath:application.properties")
public class ServicesConfig {
    @Bean
    public IOService ioService() {
        return new IOServiceImpl(System.out, System.in);
    }

    @Bean
    public QuestionMapper questionMapper() {
        return new QuestionMapperImpl();
    }

    @Bean
    public TestResultMapper testResultMapper() {
        return new TestResultMapperImpl();
    }

    @Bean
    public StudentRequestInfoService studentRequestService(IOService ioService) {
        return new StudentRequestInfoServiceImpl(ioService);
    }

    @Bean
    public TestResultCalculatorService testResultCalculatorService(
            @Value("${test.successBaselinePercent}") int successBaselinePercent) {
        return new TestResultCalculatorServiceImpl(successBaselinePercent);
    }

    @Bean
    public QuestionProcess questionProcess(IOService ioService, QuestionMapper questionMapper) {
        return new QuestionProcessImpl(ioService, questionMapper);
    }

    @Bean
    public TestService testService(
            TestDao testDao,
            QuestionProcess questionProcess,
            TestResultCalculatorService testResultCalculatorService) {
        return new TestServiceImpl(testDao, questionProcess, testResultCalculatorService);
    }


    @Bean
    public TestRunner testRunner(
            TestService testService,
            IOService ioService,
            StudentRequestInfoService studentRequestService,
            TestResultMapper testResultMapper) {
        return new TestRunner(
                testService,
                ioService,
                studentRequestService,
                testResultMapper);
    }

}
