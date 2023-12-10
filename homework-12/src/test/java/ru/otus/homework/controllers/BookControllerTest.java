package ru.otus.homework.controllers;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.config.SecurityConfiguration;
import ru.otus.homework.repositories.UserRepository;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
@Import(SecurityConfiguration.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private UserRepository userRepository;

    @WithMockUser()
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
    void listShouldRedirectToLogin() throws Exception {

        this.mvc.perform(get("/book"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @WithMockUser()
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


        this.mvc.perform(get("/book/edit/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Book1")))
                .andExpect(content().string(containsString("<input id=\"book-year-input\" value=\"2010\" name=\"year\"/>")))
                .andExpect(content().string(containsString("<option value=\"2\" selected=\"selected\">Иванов Иван</option>")))
                .andExpect(content().string(containsString("<option value=\"2\" selected=\"selected\">Horror</option>")));
    }

    @Test
    void editShouldRedirectToLogin() throws Exception {

        this.mvc.perform(get("/book/edit/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @WithMockUser()
    @Test
    void editSaveShouldCallModifyMethodOfBookService() throws Exception {
        this.mvc.perform(post("/book/edit/1").with(csrf())
                .param("id", "1")
                .param("title", "Книга")
                .param("year", "2010")
                .param("genreId", "1")
                .param("authorId", "1")
        ).andExpect(status().is(302));

        verify(bookService).modify(new UpdateBookDto(1, "Книга", Year.of(2010), 1, 1));
    }

    @Test
    void editSaveShouldRedirectToLogin() throws Exception {
        this.mvc.perform(post("/book/edit/1").with(csrf())
                .param("id", "1")
                .param("title", "Книга")
                .param("year", "2010")
                .param("genreId", "1")
                .param("authorId", "1"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrlPattern("**/login"));
    }

    @WithMockUser()
    @Test
    void createSaveShouldCallCreateMethodOfBookService() throws Exception {
        this.mvc.perform(post("/book/create").with(csrf())
                .param("title", "Книга")
                .param("year", "2010")
                .param("genreId", "1")
                .param("authorId", "1")
        ).andExpect(status().is(302));

        verify(bookService).create(new CreateBookDto("Книга", Year.of(2010), 1, 1));
    }

    @Test
    void createSaveShouldRedirectToLogin() throws Exception {
        this.mvc.perform(post("/book/create").with(csrf())
                .param("title", "Книга")
                .param("year", "2010")
                .param("genreId", "1")
                .param("authorId", "1"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrlPattern("**/login"));

    }

    @WithMockUser()
    @Test
    void removeShouldCallRemoveMethodOfBookService() throws Exception {
        this.mvc.perform(post("/book/remove/1").with(csrf()))
                .andExpect(status().is(302));

        verify(bookService).remove(1);
    }

    @Test
    void removeShouldRedirectToLogin() throws Exception {
        this.mvc.perform(post("/book/remove/1").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

    }
}