package ru.otus.homework01.dao;

import ru.otus.homework01.dao.exceptions.TestReadingException;
import ru.otus.homework01.domain.SimpleTest;

public interface TestDao {
    SimpleTest loadTest() throws TestReadingException;
}
