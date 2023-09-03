package ru.otus.homework.services.books;

import ru.otus.homework.domain.Book;
import ru.otus.homework.services.books.dto.CreateBookDto;

public interface CreateBookService {
    Book createFromInput(CreateBookDto input);
}
