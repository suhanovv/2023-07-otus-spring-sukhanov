package ru.otus.homework.repositories;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {


    private final MongoTemplate mongoTemplate;

    @Override
    public void updateBooksAuthorByAuthorId(String authorId, Author author) {
        val query = Query.query(Criteria.where("author._id").is(new ObjectId(authorId)));
        val update = new Update().set("author", author);

        mongoTemplate.updateMulti(query, update, Book.class);
    }

    @Override
    public void updateBooksGenreByGenreId(String genreId, Genre genre) {
        val query = Query.query(Criteria.where("genre._id").is(new ObjectId(genreId)));
        val update = new Update().set("genre", genre);

        mongoTemplate.updateMulti(query, update, Book.class);
    }
}
