package ru.otus.homework02.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@Getter
public class TestResultCalculatorConfig {

    @Value("${test.successBaselinePercent}")
    private int successBaselinePercent;

}
