package ru.otus.homework02.dao;

import ru.otus.homework02.dao.exceptions.TestReadingException;
import ru.otus.homework02.domain.SimpleTest;

public interface TestDao {
    SimpleTest loadTest() throws TestReadingException;
}
