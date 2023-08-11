package ru.otus.homework02.services.test;

import ru.otus.homework02.dao.exceptions.TestReadingException;
import ru.otus.homework02.domain.SimpleTest;
import ru.otus.homework02.domain.Student;
import ru.otus.homework02.domain.TestResult;


public interface TestService {

    SimpleTest loadTest() throws TestReadingException;

    TestResult executeTest(SimpleTest test, Student student);
}
