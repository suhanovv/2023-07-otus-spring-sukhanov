package ru.otus.homework02.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class TestResultCalculatorConfig {

    @Value("${test.successBaselinePercent}")
    private int successBaselinePercent;

}
