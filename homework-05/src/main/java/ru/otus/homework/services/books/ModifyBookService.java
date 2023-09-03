package ru.otus.homework.services.books;

import ru.otus.homework.domain.Book;
import ru.otus.homework.services.books.dto.UpdateBookDto;
import ru.otus.homework.services.books.exceptions.BookNotFoundException;
import ru.otus.homework.services.books.exceptions.ModifyBookException;

public interface ModifyBookService {
    Book modifyFromInput(UpdateBookDto input) throws BookNotFoundException, ModifyBookException;
}
