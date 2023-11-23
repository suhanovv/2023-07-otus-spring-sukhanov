package ru.otus.homework.controllers;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.services.authors.dto.AuthorDto;
import ru.otus.homework.services.books.BookService;
import ru.otus.homework.services.books.dto.BookDto;
import ru.otus.homework.services.genres.dto.GenreDto;

import java.time.Year;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestBookController.class)
class RestBookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @Test
    void listShouldReturnBooks() throws Exception {
        val books = List.of(
                new BookDto(1, "Book1", Year.of(2010),
                        new AuthorDto(1, "Иван", "Иванов"), new GenreDto(1, "Horror")),
                new BookDto(2, "Book2", Year.of(2000),
                        new AuthorDto(1, "Иван", "Иванов"), new GenreDto(2, "Сказки"))
        );
        when(bookService.getAll()).thenReturn(books);

        this.mvc.perform(get("/api/book"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].title").value("Book1"))
                .andExpect(jsonPath("$.[0].year").value(2010))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].title").value("Book2"))
                .andExpect(jsonPath("$.[1].year").value(2000));
        ;

    }
}