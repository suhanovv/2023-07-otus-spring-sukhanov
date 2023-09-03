package ru.otus.homework.services.books;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.AuthorDao;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.dao.GenreDao;
import ru.otus.homework.domain.Book;
import ru.otus.homework.services.books.dto.CreateBookDto;

@Service
@RequiredArgsConstructor
public class CreateBookServiceImpl implements CreateBookService {

    private final BookDao dao;

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    @Override
    public Book createFromInput(CreateBookDto input) {
        var genre = genreDao.getById(input.getGenreId());
        var author = authorDao.getById(input.getAuthorId());

        var book = new Book(input.getTitle(), input.getYear(), author, genre);

        return dao.insert(book);
    }
}
