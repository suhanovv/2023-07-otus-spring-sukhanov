package ru.otus.homework.dao.exceptions;

public class TestReadingException extends Exception {

    public TestReadingException(Throwable cause) {
        super(cause);
    }

    public TestReadingException(String message) {
        super(message);
    }
}
