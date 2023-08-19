package ru.otus.homework.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "test-dao.csv")
public class DaoConfig {

    private String sourcePath;

    private String colDelimiter;

    private String answersDelimiter;

    private String validationDelimiter;
}
