package ru.otus.homework.services.books;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.domain.Book;
import ru.otus.homework.services.authors.FindAuthorService;
import ru.otus.homework.services.authors.exceptions.AuthorNotFoundException;
import ru.otus.homework.services.books.dto.UpdateBookDto;
import ru.otus.homework.services.books.exceptions.BookNotFoundException;
import ru.otus.homework.services.books.exceptions.ModifyBookException;
import ru.otus.homework.services.genres.FindGenreService;
import ru.otus.homework.services.genres.exceptions.GenreNotFoundException;

@Service
@RequiredArgsConstructor
public class ModifyBookServiceImpl implements ModifyBookService {

    private final BookDao dao;

    private final FindAuthorService findAuthorService;

    private final FindGenreService findGenreService;

    private final FindBookService findBookService;

    @Override
    public Book modifyFromInput(UpdateBookDto input) throws BookNotFoundException, ModifyBookException {

        var book = findBookService.get(input.getId());

        updateTitleFromInput(book, input);
        updateYearFromInput(book, input);
        updateAuthorFromInput(book, input);
        updateGenreFromInput(book, input);

        dao.update(book);

        return book;
    }

    private void updateTitleFromInput(Book book, UpdateBookDto input) {
        if (input.getTitle() != null) {
            book.setTitle(input.getTitle());
        }
    }

    private void updateYearFromInput(Book book, UpdateBookDto input) {
        if (input.getYear() != 0) {
            book.setYear(input.getYear());
        }
    }

    private void updateAuthorFromInput(Book book, UpdateBookDto input) throws ModifyBookException {
        if (input.getAuthorId() != 0) {
            try {
                var author = findAuthorService.get(input.getAuthorId());
                book.setAuthor(author);
            } catch (AuthorNotFoundException e) {
                throw new ModifyBookException(e);
            }
        }
    }

    private void updateGenreFromInput(Book book, UpdateBookDto input) throws ModifyBookException {
        if (input.getGenreId() != 0) {
            try {
                var genre = findGenreService.get(input.getGenreId());
                book.setGenre(genre);
            } catch (GenreNotFoundException e) {
                throw new ModifyBookException(e);
            }
        }
    }


}
