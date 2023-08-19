package ru.otus.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.homework.config.DaoConfig;
import ru.otus.homework.config.LocaleIOConfig;
import ru.otus.homework.config.TestResultCalculatorConfig;

@EnableConfigurationProperties({TestResultCalculatorConfig.class, DaoConfig.class, LocaleIOConfig.class})
@SpringBootApplication
public class Homework03Application {

    public static void main(String[] args) {
        SpringApplication.run(Homework03Application.class, args);
    }

}
