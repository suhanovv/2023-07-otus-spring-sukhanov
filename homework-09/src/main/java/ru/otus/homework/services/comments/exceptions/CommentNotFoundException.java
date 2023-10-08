package ru.otus.homework.services.comments.exceptions;

public class CommentNotFoundException extends Exception {
    public  CommentNotFoundException(String message) {
        super(message);
    }
}