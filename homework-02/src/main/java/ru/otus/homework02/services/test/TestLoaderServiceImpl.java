package ru.otus.homework02.services.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.homework02.dao.TestDao;
import ru.otus.homework02.dao.exceptions.TestReadingException;
import ru.otus.homework02.domain.SimpleTest;

@Service
public class TestLoaderServiceImpl implements TestLoaderService {

    private final TestDao dao;

    @Autowired
    public TestLoaderServiceImpl(TestDao dao) {
        this.dao = dao;
    }

    @Override
    public SimpleTest load() throws TestReadingException {
        return dao.loadTest();
    }
}
