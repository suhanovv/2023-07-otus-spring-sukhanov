package ru.otus.homework01.dao;

import io.vavr.control.Either;
import ru.otus.homework01.domain.SimpleTest;

public interface TestDao {
    Either<String, SimpleTest> loadTest();
}
