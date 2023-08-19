package ru.otus.homework.services.test;

import ru.otus.homework.domain.SimpleTest;
import ru.otus.homework.domain.Student;
import ru.otus.homework.domain.TestResult;


public interface TestService {

    TestResult executeTest(SimpleTest test, Student student);
}
