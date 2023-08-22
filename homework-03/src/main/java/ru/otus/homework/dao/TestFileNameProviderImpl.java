package ru.otus.homework.dao;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.homework.config.DaoConfig;
import ru.otus.homework.config.LocaleProvider;

@Component
@AllArgsConstructor
public class TestFileNameProviderImpl implements TestFileNameProvider {

    private final DaoConfig daoConfig;

    private final LocaleProvider localeProvider;

    @Override
    public String getFilename() {
        String lang = localeProvider.getCurrent().getLanguage();

        return daoConfig.getSourcePath() + lang + ".csv";
    }
}
