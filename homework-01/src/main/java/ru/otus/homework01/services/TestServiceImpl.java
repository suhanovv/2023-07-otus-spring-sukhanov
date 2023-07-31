package ru.otus.homework01.services;

import io.vavr.control.Either;
import ru.otus.homework01.dao.TestDao;
import ru.otus.homework01.domain.Question;
import ru.otus.homework01.domain.SimpleTest;
import ru.otus.homework01.mappers.QuestionToStringMapper;

public class TestServiceImpl implements TestService {
    private final TestDao dao;

    private final PresenterService presenter;

    public TestServiceImpl(TestDao dao, PresenterService presenter) {
        this.dao = dao;
        this.presenter = presenter;
    }

    @Override
    public void runTest() {
        Either<String, SimpleTest> result = dao.loadTest();
        if (result.isLeft()) {
            presenter.display(result.getLeft());
            return;
        }

        for (Question question : result.get().getQuestions()) {
            String questionString = QuestionToStringMapper.map(question);
            presenter.display(questionString);
        }
    }
}
