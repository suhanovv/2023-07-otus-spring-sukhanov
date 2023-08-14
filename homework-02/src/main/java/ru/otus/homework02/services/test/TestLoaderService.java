package ru.otus.homework02.services.test;

import ru.otus.homework02.dao.exceptions.TestReadingException;
import ru.otus.homework02.domain.SimpleTest;

public interface TestLoaderService {
    SimpleTest load() throws TestReadingException;
}
