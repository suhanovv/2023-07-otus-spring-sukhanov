package ru.otus.homework.services.genres;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;
import ru.otus.homework.repositories.AuthorRepository;
import ru.otus.homework.repositories.BookRepository;
import ru.otus.homework.repositories.GenreRepository;
import ru.otus.homework.services.genres.dto.UpdateGenreDto;
import ru.otus.homework.services.genres.exceptions.GenreAlreadyUsedException;
import ru.otus.homework.services.genres.exceptions.GenreNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataMongoTest
@Import(GenreServiceImpl.class)
class GenreServiceImplTest {

    @Autowired
    GenreService genreService;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void modifyShouldAlsoUpdateGenreInBook() throws GenreNotFoundException {
        val author = authorRepository.save(new Author("Иван", "Иванов"));
        val genre = genreRepository.save(new Genre("сказки"));
        val book = bookRepository.save(new Book("Книга", 2000, author, genre));

        val updateGenreDto = new UpdateGenreDto(genre.getId(), "Триллер");

        val updatedGenre = genreService.modify(updateGenreDto);
        val updatedBook = bookRepository.findById(book.getId());

        assertThat(updatedGenre.getName()).isEqualTo(updateGenreDto.getName());
        assertThat(updatedBook.get().getGenre()).isEqualTo(updatedGenre);
    }

    @Test
    void removeShouldRaiseExceptionIfBookExists() {
        val author = authorRepository.save(new Author("Иван", "Иванов"));
        val genre = genreRepository.save(new Genre("сказки"));
        bookRepository.save(new Book("Книга", 2000, author, genre));

        assertThatThrownBy(
                () -> genreService.remove(genre.getId())
        ).isInstanceOf(GenreAlreadyUsedException.class);
    }
}