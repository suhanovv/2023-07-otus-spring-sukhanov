package ru.otus.homework.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import reactor.core.publisher.Flux;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;
import ru.otus.homework.repositories.AuthorRepository;
import ru.otus.homework.repositories.BookRepository;
import ru.otus.homework.repositories.GenreRepository;

import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;


@ChangeLog
public class InitDatabaseChangelog {

    private List<Author> authors;

    private List<Genre> genres;

    private List<Book> books;

    @ChangeSet(order = "001", id = "dropDb", author = "vadimsuhanov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "initDb", author = "vadimsuhanov", runAlways = true)
    public void initDb(
            GenreRepository genreRepository, AuthorRepository authorRepository, BookRepository bookRepository) {
        createGenres(genreRepository);
        createAuthors(authorRepository);
        createBooks(bookRepository);
    }

    private void createAuthors(AuthorRepository repo) {
        var author1 = repo.save(new Author("Роберт", "Мартин"));
        var author2 = repo.save(new Author("Герберт", "Шилдт"));
        var author3 = repo.save(new Author("Юрий", "Никитин"));
        var author4 = repo.save(new Author("Ник", "Перумов"));
        var author5 = repo.save(new Author("Максим", "Дорофеев"));
        authors = Flux.concat(author1, author2, author3, author4, author5).toStream().collect(Collectors.toList());
    }

    private void createGenres(GenreRepository repo) {
        var genre1 = repo.save(new Genre("Техническая литература"));
        var genre2 = repo.save(new Genre("Фантастика"));
        var genre3 = repo.save(new Genre("Нехудожественная литература"));
        genres = Flux.concat(genre1, genre2, genre3).toStream().collect(Collectors.toList());
    }

    private void createBooks(BookRepository repo) {

        var book1 = repo.save(new Book("Чистый код", Year.of(2019), authors.get(0), genres.get(0)));
        var book2 = repo.save(new Book("Чистая архитектура", Year.of(2021), authors.get(0), genres.get(0)));
        var book3 = repo.save(
                new Book("Java. Полное руководство. Десятое издание", Year.of(2019), authors.get(1), genres.get(0)));
        var book4 = repo.save(new Book("Трое из леса", Year.of(2007), authors.get(2), genres.get(1)));
        books = Flux.merge(book1, book2, book3, book4).toStream().collect(Collectors.toList());
    }
}
