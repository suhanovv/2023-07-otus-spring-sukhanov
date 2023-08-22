package ru.otus.homework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "localization")
public class LocaleIOConfig implements LocaleProvider {

    private final Locale locale;

    public LocaleIOConfig(String locale) {
        this.locale = Locale.forLanguageTag(locale);
    }

    @Override
    public Locale getCurrent() {
        return locale;
    }
}
