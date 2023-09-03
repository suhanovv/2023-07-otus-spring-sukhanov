package ru.otus.homework.dao;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public Author insert(Author author) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcOperations.update(
                "insert into authors (first_name, last_name) values (:first_name, :last_name)",
                new MapSqlParameterSource(Map.of(
                        "first_name", author.getFirstName(),
                        "last_name", author.getLastName())),
                keyHolder);
        author.setId(keyHolder.getKey().longValue());

        return author;
    }

    @Override
    public void update(Author author) {
        namedParameterJdbcOperations.update(
                "update authors set " +
                        "first_name = :first_name, " +
                        "last_name = :last_name " +
                        "where id = :id",
                Map.of(
                        "id", author.getId(),
                        "first_name", author.getFirstName(),
                        "last_name", author.getLastName()));
    }

    @Override
    public Author getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select id, first_name, last_name from authors where id = :id", params, new AuthorDaoJdbc.AuthorMapper()
        );
    }

    @Override
    public List<Author> getAll() {
        return namedParameterJdbcOperations.query(
                "select id, first_name, last_name from authors", new AuthorDaoJdbc.AuthorMapper()
        );
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        var affectedRows = namedParameterJdbcOperations.update(
                "delete from authors where id = :id", params
        );
        if (affectedRows == 0) {
            throw new EmptyResultDataAccessException(1);
        }
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            return new Author(id, firstName, lastName);
        }
    }
}
