package ru.otus.homework.dao;

import ru.otus.homework.dao.exceptions.TestReadingException;
import ru.otus.homework.domain.SimpleTest;

public interface TestDao {
    SimpleTest loadTest() throws TestReadingException;
}
