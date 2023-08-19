package ru.otus.homework.services.test;

import ru.otus.homework.dao.exceptions.TestReadingException;
import ru.otus.homework.domain.SimpleTest;

public interface TestLoaderService {
    SimpleTest load() throws TestReadingException;
}
