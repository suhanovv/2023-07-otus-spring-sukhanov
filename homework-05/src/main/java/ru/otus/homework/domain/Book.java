package ru.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Book {
    private long id;

    @NonNull
    private String title;

    @NonNull
    private int year;

    @NonNull
    private Author author;

    @NonNull
    private Genre genre;
}
