package ru.otus.homework.services.localization;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.homework.config.LocaleProvider;

@Service
@AllArgsConstructor
public class LocalizationServiceImpl implements LocalizationService {
    private final LocaleProvider localeProvider;

    private final MessageSource messageSource;

    @Override
    public String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, localeProvider.getCurrent());
    }
}
