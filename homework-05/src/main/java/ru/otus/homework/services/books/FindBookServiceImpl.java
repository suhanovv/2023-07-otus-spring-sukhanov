package ru.otus.homework.services.books;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.domain.Book;
import ru.otus.homework.services.books.exceptions.BookNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindBookServiceImpl implements FindBookService {

    private final BookDao dao;

    @Override
    public Book get(long id) throws BookNotFoundException {
        try {
            return dao.getById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new BookNotFoundException("Book with id: " + id + " not found");
        }
    }

    @Override
    public List<Book> getAll() {
        return dao.getAll();
    }
}
