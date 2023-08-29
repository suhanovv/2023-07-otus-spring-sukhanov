package ru.otus.homework.services.localization;

public interface LocalizationService {
    String getMessage(String key, Object ...args);
}
