package ru.otus.homework.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "comments")
public class Comment {
    @Id
    private String id;

    @DBRef(lazy = true)
    private Book book;

    private String commentText;

    public Comment(Book book, String commentText) {
        this.book = book;
        this.commentText = commentText;
    }
}
