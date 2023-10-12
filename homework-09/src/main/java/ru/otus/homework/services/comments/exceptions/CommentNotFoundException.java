package ru.otus.homework.services.comments.exceptions;

public class CommentNotFoundException extends RuntimeException {
    public  CommentNotFoundException(String message) {
        super(message);
    }
}