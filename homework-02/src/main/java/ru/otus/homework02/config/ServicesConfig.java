package ru.otus.homework02.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import ru.otus.homework02.services.io.IOService;
import ru.otus.homework02.services.io.IOServiceImpl;


@Configuration
@PropertySource("classpath:application.properties")
public class ServicesConfig {

    @Bean
    public IOService ioService() {
        return new IOServiceImpl(System.out, System.in);
    }

}
