package ru.otus.homework.dao;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
@Configuration
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public Book insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcOperations.update(
                "insert into books (title, `year`, author_id, genre_id) " +
                        "values (:title, :year, :author_id, :genre_id)",
                new MapSqlParameterSource(Map.of(
                        "title", book.getTitle(),
                        "year", book.getYear(),
                        "author_id", book.getAuthor().getId(),
                        "genre_id", book.getGenre().getId())),
                keyHolder);
        book.setId(keyHolder.getKey().longValue());
        return book;
    }

    @Override
    public void update(Book book) {
        namedParameterJdbcOperations.update(
                "update books set " +
                        "title = :title, " +
                        "`year` = :year, " +
                        "author_id = :author_id, " +
                        "genre_id = :genre_id " +
                        "where id = :id",
                Map.of(
                        "id", book.getId(),
                        "title", book.getTitle(),
                        "year", book.getYear(),
                        "author_id", book.getAuthor().getId(),
                        "genre_id", book.getGenre().getId()));
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select b.id, b.title, b.`year`, b.author_id, b.genre_id, " +
                        "a.first_name, a.last_name, g.name " +
                        "from books b " +
                        "join authors a on a.id = b.author_id " +
                        "join genres g on g.id = b.genre_id " +
                        "where b.id = :id", params, new BookDaoJdbc.BookMapper()
        );
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.query(
                "select b.id, b.title, b.`year`, b.author_id, b.genre_id, " +
                        "a.first_name, a.last_name, g.name " +
                        "from books b " +
                        "join authors a on a.id = b.author_id " +
                        "join genres g on g.id = b.genre_id", new BookDaoJdbc.BookMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        var affectedRows = namedParameterJdbcOperations.update(
                "delete from books where id = :id", params
        );
        if (affectedRows == 0) {
            throw new EmptyResultDataAccessException(1);
        }
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long authorId = resultSet.getLong("books.author_id");
            String firstName = resultSet.getString("authors.first_name");
            String lastName = resultSet.getString("authors.last_name");
            Author author = new Author(authorId, firstName, lastName);

            long genreId = resultSet.getLong("books.genre_id");
            String genreName = resultSet.getString("genres.name");
            Genre genre = new Genre(genreId, genreName);

            long id = resultSet.getLong("books.id");
            String title = resultSet.getString("books.title");
            int year = resultSet.getInt("books.year");

            return new Book(id, title, year, author, genre);
        }
    }
}
