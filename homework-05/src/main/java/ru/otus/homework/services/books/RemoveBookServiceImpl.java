package ru.otus.homework.services.books;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.services.books.exceptions.BookNotFoundException;

@Service
@RequiredArgsConstructor
public class RemoveBookServiceImpl implements RemoveBookService {

    private final BookDao dao;

    @Override
    public void remove(long id) throws BookNotFoundException {
        try {
            dao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new BookNotFoundException("Book with id: " + id + "doesn't exists");
        }
    }
}
