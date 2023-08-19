package ru.otus.homework.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "test")
public class TestResultCalculatorConfig {

    private int successBaselinePercent;

}
