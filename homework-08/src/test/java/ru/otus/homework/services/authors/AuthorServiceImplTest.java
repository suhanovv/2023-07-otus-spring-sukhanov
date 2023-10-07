package ru.otus.homework.services.authors;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.homework.models.Author;
import ru.otus.homework.models.Book;
import ru.otus.homework.models.Genre;
import ru.otus.homework.services.authors.dto.UpdateAuthorDto;
import ru.otus.homework.services.authors.exceptions.AuthorAlreadyUsedException;
import ru.otus.homework.services.authors.exceptions.AuthorNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataMongoTest
@Import(AuthorServiceImpl.class)
class AuthorServiceImplTest {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void modifyShouldAlsoUpdateAuthorInBook() throws AuthorNotFoundException {
        val author = mongoTemplate.save(new Author("Иван", "Иванов"));
        val genre = mongoTemplate.save(new Genre("сказки"));
        val book = mongoTemplate.save(new Book("Книга", 2000, author, genre));
        val updateAuthorDto = new UpdateAuthorDto(author.getId(), "Петр", "Петров");

        val updatedAuthor = authorService.modify(updateAuthorDto);
        val updatedBook = mongoTemplate.findById(book.getId(), Book.class);

        assertThat(updatedAuthor.getFirstName()).isEqualTo(updateAuthorDto.getFirstName());
        assertThat(updatedAuthor.getLastName()).isEqualTo(updateAuthorDto.getLastName());
        assertThat(updatedBook.getAuthor()).isEqualTo(updatedAuthor);
    }

    @Test
    void removeShouldRaiseExceptionIfBookExists() {
        val author = mongoTemplate.save(new Author("Иван", "Иванов"));
        val genre = mongoTemplate.save(new Genre("сказки"));
        mongoTemplate.save(new Book("Книга", 2000, author, genre));

        assertThatThrownBy(
                () -> authorService.remove(author.getId())
        ).isInstanceOf(AuthorAlreadyUsedException.class);

    }
}