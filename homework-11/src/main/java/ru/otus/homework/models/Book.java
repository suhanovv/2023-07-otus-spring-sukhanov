package ru.otus.homework.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Year;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class Book {
    @Id
    private String id;

    private String title;

    private Year publishYear;

    private Author author;

    private Genre genre;

    public Book(String title, Year publishYear, Author author, Genre genre) {
        this.title = title;
        this.publishYear = publishYear;
        this.author = author;
        this.genre = genre;
    }
}

