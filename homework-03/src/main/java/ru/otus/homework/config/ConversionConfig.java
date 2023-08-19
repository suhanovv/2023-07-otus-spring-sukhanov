package ru.otus.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;
import ru.otus.homework.domain.Question;

import java.util.Set;

@Configuration
public class ConversionConfig {

    @Bean
    ConversionServiceFactoryBean conversionService(
            Converter<Question, String> questionStringConverter) {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(
                Set.of(questionStringConverter)
        );
        return bean;
    }
}
