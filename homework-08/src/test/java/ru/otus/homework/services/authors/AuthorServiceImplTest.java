package ru.otus.homework.services.authors;

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
import ru.otus.homework.services.authors.dto.UpdateAuthorDto;
import ru.otus.homework.services.authors.exceptions.AuthorAlreadyUsedException;
import ru.otus.homework.services.authors.exceptions.AuthorNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataMongoTest
@Import(AuthorServiceImpl.class)
class AuthorServiceImplTest {

    @Autowired
    AuthorService authorService;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void modifyShouldAlsoUpdateAuthorInBook() throws AuthorNotFoundException {
        val author = authorRepository.save(new Author("Иван", "Иванов"));
        val genre = genreRepository.save(new Genre("сказки"));
        val book = bookRepository.save(new Book("Книга", 2000, author, genre));
        val updateAuthorDto = new UpdateAuthorDto(author.getId(), "Петр", "Петров");

        val updatedAuthor = authorService.modify(updateAuthorDto);
        val updatedBook = bookRepository.findById(book.getId());

        assertThat(updatedAuthor.getFirstName()).isEqualTo(updateAuthorDto.getFirstName());
        assertThat(updatedAuthor.getLastName()).isEqualTo(updateAuthorDto.getLastName());
        assertThat(updatedBook.get().getAuthor()).isEqualTo(updatedAuthor);
    }

    @Test
    void removeShouldRaiseExceptionIfBookExists() {
        val author = authorRepository.save(new Author("Иван", "Иванов"));
        val genre = genreRepository.save(new Genre("сказки"));
        bookRepository.save(new Book("Книга", 2000, author, genre));

        assertThatThrownBy(
                () -> authorService.remove(author.getId())
        ).isInstanceOf(AuthorAlreadyUsedException.class);

    }
}