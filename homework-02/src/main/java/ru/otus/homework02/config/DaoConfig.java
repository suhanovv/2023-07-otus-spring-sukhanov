package ru.otus.homework02.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.homework02.dao.TestDao;
import ru.otus.homework02.dao.TestDaoCsv;

@PropertySource("classpath:application.properties")
@Configuration
public class DaoConfig {

    @Bean
    public TestDao testDao(
            @Value("${testDao.csv.sourcePath}") String sourcePath,
            @Value("${testDao.csv.colDelimiter}") String colDelimiter,
            @Value("${testDao.csv.answersDelimiter}") String answersDelimiter,
            @Value("${testDao.csv.validationDelimiter}") String validationDelimiter) {
        return new TestDaoCsv(sourcePath, colDelimiter, answersDelimiter, validationDelimiter);
    }
}
