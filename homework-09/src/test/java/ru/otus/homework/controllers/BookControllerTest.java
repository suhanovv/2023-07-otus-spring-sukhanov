package ru.otus.homework.controllers;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.services.authors.AuthorService;
import ru.otus.homework.services.authors.dto.AuthorDto;
import ru.otus.homework.services.books.BookService;
import ru.otus.homework.services.books.dto.BookDto;
import ru.otus.homework.services.books.dto.CreateBookDto;
import ru.otus.homework.services.books.dto.UpdateBookDto;
import ru.otus.homework.services.genres.GenreService;
import ru.otus.homework.services.genres.dto.GenreDto;

import java.time.Year;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    AuthorService authorService;

    @MockBean
    GenreService genreService;

    @Test
    void listShouldRenderBooks() throws Exception {
        val books = List.of(
                new BookDto(1, "Book1", Year.of(2010),
                        new AuthorDto(1, "Иван", "Иванов"), new GenreDto(1, "Horror")),
                new BookDto(2, "Book2", Year.of(2000),
                        new AuthorDto(1, "Иван", "Иванов"), new GenreDto(2, "Сказки"))
                );
        when(bookService.getAll()).thenReturn(books);

        this.mvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Book1")))
                .andExpect(content().string(containsString("Book2")));
    }

    @Test
    void editShouldRenderBookWithValidSelectedOptions() throws Exception {
        val book = new BookDto(1, "Book1", Year.of(2010),
                new AuthorDto(2, "Иван", "Иванов"), new GenreDto(2, "Horror"));
        when(bookService.get(1)).thenReturn(book);
        when(authorService.getAll()).thenReturn(List.of(
                new AuthorDto(1, "Петр", "Сергеев"),
                new AuthorDto(2, "Иван", "Иванов"),
                new AuthorDto(3, "Вадим", "Иванов")
        ));
        when(genreService.getAll()).thenReturn(List.of(
                new GenreDto(1, "Научпоп"),
                new GenreDto(2, "Horror"),
                new GenreDto(3, "Сказки")
        ));


        this.mvc.perform(get("/book/edit?id=1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Book1")))
                .andExpect(content().string(containsString("<option value=\"2010\" selected=\"selected\">2010</option>")))
                .andExpect(content().string(containsString("<option value=\"2\" selected=\"selected\">Иванов Иван</option>")))
                .andExpect(content().string(containsString("<option value=\"2\" selected=\"selected\">Horror</option>")));
    }

    @Test
    void editSaveShouldCallModifyMethodOfBookService() throws Exception {
        this.mvc.perform(post("/book/edit?id=1")
                .param("id", "1")
                .param("title", "Книга")
                .param("year", "2010")
                .param("genreId", "1")
                .param("authorId", "1")
        ).andExpect(status().is(302));

        verify(bookService).modify(new UpdateBookDto(1, "Книга", Year.of(2010), 1, 1));
    }

    @Test
    void createSaveShouldCallCreateMethodOfBookService() throws Exception {
        this.mvc.perform(post("/book/create")
                .param("title", "Книга")
                .param("year", "2010")
                .param("genreId", "1")
                .param("authorId", "1")
        ).andExpect(status().is(302));

        verify(bookService).create(new CreateBookDto("Книга", Year.of(2010), 1, 1));
    }

    @Test
    void removeShouldCallRemoveMethodOfBookService() throws Exception {
        this.mvc.perform(post("/book/remove?id=1"))
                .andExpect(status().is(302));

        verify(bookService).remove(1);
    }
}