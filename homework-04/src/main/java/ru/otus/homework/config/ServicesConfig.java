package ru.otus.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.otus.homework.services.io.IOService;
import ru.otus.homework.services.io.IOServiceImpl;


@Configuration
public class ServicesConfig {

    @Bean
    public IOService ioService() {
        return new IOServiceImpl(System.out, System.in);
    }

}
