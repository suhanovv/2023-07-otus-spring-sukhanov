package ru.otus.homework.services.books;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.models.Book;
import ru.otus.homework.repositories.BookRepository;
import ru.otus.homework.services.authors.AuthorService;
import ru.otus.homework.services.authors.exceptions.AuthorNotFoundException;
import ru.otus.homework.services.books.dto.CreateBookDto;
import ru.otus.homework.services.books.dto.UpdateBookDto;
import ru.otus.homework.services.books.exceptions.BookNotFoundException;
import ru.otus.homework.services.books.exceptions.CreateBookException;
import ru.otus.homework.services.books.exceptions.ModifyBookException;
import ru.otus.homework.services.genres.GenreService;
import ru.otus.homework.services.genres.exceptions.GenreNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    private final AuthorService authorService;

    private final GenreService genreService;

    @Transactional
    @Override
    public Book create(CreateBookDto book) throws CreateBookException {
        try {
            var genre = genreService.get(book.getGenreId());
            var author = authorService.get(book.getAuthorId());

            var newBook = new Book(0, book.getTitle(), book.getYear(), author, genre);

            return repository.insert(newBook);
        } catch (GenreNotFoundException | AuthorNotFoundException e) {
            throw new CreateBookException(e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Book get(long id) throws BookNotFoundException {
        return repository.getById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id: " + id + " not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAll() {
        return repository.getAll();
    }

    @Transactional
    @Override
    public Book modify(UpdateBookDto book) throws BookNotFoundException, ModifyBookException {

        var oldBook = get(book.getId());

        updateTitle(oldBook, book);
        updateYear(oldBook, book);
        updateAuthor(oldBook, book);
        updateGenre(oldBook, book);

        repository.update(oldBook);

        return oldBook;
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
        if (book.getYear() != 0) {
            oldBook.setPublishYear(book.getYear());
        }
    }

    private void updateAuthor(Book oldBook, UpdateBookDto book) throws ModifyBookException {
        if (book.getAuthorId() != 0) {
            try {
                var author = authorService.get(book.getAuthorId());
                oldBook.setAuthor(author);
            } catch (AuthorNotFoundException e) {
                throw new ModifyBookException(e);
            }
        }
    }

    private void updateGenre(Book oldBook, UpdateBookDto book) throws ModifyBookException {
        if (book.getGenreId() != 0) {
            try {
                var genre = genreService.get(book.getGenreId());
                oldBook.setGenre(genre);
            } catch (GenreNotFoundException e) {
                throw new ModifyBookException(e);
            }
        }
    }
}
