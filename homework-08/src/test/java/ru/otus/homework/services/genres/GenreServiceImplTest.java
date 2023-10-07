package ru.otus.homework.services.genres;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;
import ru.otus.homework.services.genres.dto.UpdateGenreDto;
import ru.otus.homework.services.genres.exceptions.GenreAlreadyUsedException;
import ru.otus.homework.services.genres.exceptions.GenreNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataMongoTest
@Import(GenreServiceImpl.class)
class GenreServiceImplTest {

    @Autowired
    private GenreService genreService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void modifyShouldAlsoUpdateGenreInBook() throws GenreNotFoundException {
        val author = mongoTemplate.save(new Author("Иван", "Иванов"));
        val genre = mongoTemplate.save(new Genre("сказки"));
        val book = mongoTemplate.save(new Book("Книга", 2000, author, genre));

        val updateGenreDto = new UpdateGenreDto(genre.getId(), "Триллер");

        val updatedGenre = genreService.modify(updateGenreDto);
        val updatedBook = mongoTemplate.findById(book.getId(), Book.class);

        assertThat(updatedGenre.getName()).isEqualTo(updateGenreDto.getName());
        assertThat(updatedBook.getGenre()).isEqualTo(updatedGenre);
    }

    @Test
    void removeShouldRaiseExceptionIfBookExists() {
        val author = mongoTemplate.save(new Author("Иван", "Иванов"));
        val genre = mongoTemplate.save(new Genre("сказки"));
        mongoTemplate.save(new Book("Книга", 2000, author, genre));

        assertThatThrownBy(
                () -> genreService.remove(genre.getId())
        ).isInstanceOf(GenreAlreadyUsedException.class);
    }
}