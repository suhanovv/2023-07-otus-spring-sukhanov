package ru.otus.homework.services.io;

public interface InputService {
    String readStringWithPrompt(String s);

    int readIntWithPrompt(String s);

    int readInt();
}
