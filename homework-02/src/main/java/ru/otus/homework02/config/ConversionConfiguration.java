package ru.otus.homework02.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import ru.otus.homework02.converters.QuestionToStringConverter;
import ru.otus.homework02.converters.TestResultToStringConverter;

import java.util.Set;

@Configuration
public class ConversionConfiguration {

    @Bean
    ConversionServiceFactoryBean conversionService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(
                Set.of(
                        new QuestionToStringConverter(),
                        new TestResultToStringConverter()
                )
        );
        return bean;
    }
}
