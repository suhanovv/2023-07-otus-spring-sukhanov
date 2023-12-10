package ru.otus.homework.services.books;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.models.Book;
import ru.otus.homework.repositories.BookRepository;
import ru.otus.homework.services.authors.AuthorService;
import ru.otus.homework.services.authors.dto.AuthorDto;
import ru.otus.homework.services.authors.exceptions.AuthorNotFoundException;
import ru.otus.homework.services.books.dto.BookDto;
import ru.otus.homework.services.books.dto.CreateBookDto;
import ru.otus.homework.services.books.dto.UpdateBookDto;
import ru.otus.homework.services.books.exceptions.BookNotFoundException;
import ru.otus.homework.services.books.exceptions.CreateBookException;
import ru.otus.homework.services.books.exceptions.ModifyBookException;
import ru.otus.homework.services.genres.GenreService;
import ru.otus.homework.services.genres.dto.GenreDto;
import ru.otus.homework.services.genres.exceptions.GenreNotFoundException;

import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    private final AuthorService authorService;

    private final GenreService genreService;

    @Transactional
    @Override
    public BookDto create(CreateBookDto book) {
        try {
            var genre = genreService.get(book.getGenreId());
            var author = authorService.get(book.getAuthorId());

            var newBook = new Book(
                    0, book.getTitle(), book.getYear(), AuthorDto.toDomain(author), GenreDto.toDomain(genre));

            return BookDto.toDto(repository.save(newBook));
        } catch (GenreNotFoundException | AuthorNotFoundException e) {
            throw new CreateBookException(e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public BookDto get(long id) {
        return BookDto.toDto(getDomainBook(id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> getAll() {

        return repository.findAll().stream().map(BookDto::toDto).toList();
    }

    @Transactional
    @Override
    public BookDto modify(UpdateBookDto book) {

        var oldBook = getDomainBook(book.getId());

        updateTitle(oldBook, book);
        updateYear(oldBook, book);
        updateAuthor(oldBook, book);
        updateGenre(oldBook, book);

        repository.save(oldBook);

        return BookDto.toDto(oldBook);
    }


    @Transactional
    @Override
    public void remove(long id) {
        repository.deleteById(id);
    }

    private void updateTitle(Book oldBook, UpdateBookDto book) {
        if (book.getTitle() != null) {
            oldBook.setTitle(book.getTitle());
        }
    }

    private void updateYear(Book oldBook, UpdateBookDto book) {
        if (!book.getYear().equals(Year.of(0))) {
            oldBook.setPublishYear(book.getYear());
        }
    }

    private void updateAuthor(Book oldBook, UpdateBookDto book) {
        if (book.getAuthorId() != 0) {
            try {
                var author = authorService.get(book.getAuthorId());
                oldBook.setAuthor(AuthorDto.toDomain(author));
            } catch (AuthorNotFoundException e) {
                throw new ModifyBookException(e);
            }
        }
    }

    private void updateGenre(Book oldBook, UpdateBookDto book) {
        if (book.getGenreId() != 0) {
            try {
                var genre = genreService.get(book.getGenreId());
                oldBook.setGenre(GenreDto.toDomain(genre));
            } catch (GenreNotFoundException e) {
                throw new ModifyBookException(e);
            }
        }
    }

    private Book getDomainBook(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id: " + id + " not found"));
    }
}
