package ru.otus.homework01.services;

import ru.otus.homework01.dao.TestDao;
import ru.otus.homework01.dao.exceptions.TestReadingException;
import ru.otus.homework01.domain.Question;
import ru.otus.homework01.domain.SimpleTest;
import ru.otus.homework01.mappers.QuestionToStringMapper;

public class TestServiceImpl implements TestService {
    private final TestDao dao;

    private final PresenterService presenter;

    private final QuestionToStringMapper mapper;

    public TestServiceImpl(TestDao dao, PresenterService presenter, QuestionToStringMapper mapper) {
        this.dao = dao;
        this.presenter = presenter;
        this.mapper = mapper;
    }

    @Override
    public void runTest() {
        try {
            SimpleTest result = dao.loadTest();
            for (Question question : result.getQuestions()) {
                String questionString = mapper.map(question);
                presenter.display(questionString);
            }
        } catch (TestReadingException e) {
            presenter.display("Test loading error: " + e.getMessage());
        }

    }
}
