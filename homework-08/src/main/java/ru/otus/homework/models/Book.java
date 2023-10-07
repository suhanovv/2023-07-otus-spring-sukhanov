package ru.otus.homework.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class Book {
    @Id
    private String id;

    private String title;

    private int publishYear;

    private Author author;

    private Genre genre;

    public Book(String title, int publishYear, Author author, Genre genre) {
        this.title = title;
        this.publishYear = publishYear;
        this.author = author;
        this.genre = genre;
    }
}
