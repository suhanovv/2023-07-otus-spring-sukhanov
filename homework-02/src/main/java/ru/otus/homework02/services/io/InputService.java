package ru.otus.homework02.services.io;

public interface InputService {
    String readStringWithPrompt(String s);

    int readIntWithPrompt(String s);

    int readInt();
}
