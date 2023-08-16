package ru.otus.homework02.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class DaoConfig {

    @Value("${testDao.csv.sourcePath}")
    private String sourcePath;

    @Value("${testDao.csv.colDelimiter}")
    private String colDelimiter;

    @Value("${testDao.csv.answersDelimiter}")
    private String answersDelimiter;

    @Value("${testDao.csv.validationDelimiter}")
    private String validationDelimiter;

}
