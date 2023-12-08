package ru.otus.homework.repositories;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Mono;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {


    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<Void> updateBooksAuthorByAuthorId(String authorId, Author author) {
        val query = Query.query(Criteria.where("author._id").is(new ObjectId(authorId)));
        val update = new Update().set("author", author);

        return mongoTemplate.updateMulti(query, update, Book.class).then();
    }

    @Override
    public Mono<Void> updateBooksGenreByGenreId(String genreId, Genre genre) {
        val query = Query.query(Criteria.where("genre._id").is(new ObjectId(genreId)));
        val update = new Update().set("genre", genre);

        return mongoTemplate.updateMulti(query, update, Book.class).then();
    }
}
