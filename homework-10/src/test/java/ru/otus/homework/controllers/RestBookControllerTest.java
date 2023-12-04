package ru.otus.homework.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.services.authors.dto.AuthorDto;
import ru.otus.homework.services.books.BookService;
import ru.otus.homework.services.books.dto.BookDto;
import ru.otus.homework.services.books.dto.CreateBookDto;
import ru.otus.homework.services.books.dto.UpdateBookDto;
import ru.otus.homework.services.genres.dto.GenreDto;

import java.time.Year;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestBookController.class)
class RestBookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void listShouldReturnBooks() throws Exception {
        val expectedBooks = List.of(
                new BookDto(1, "Book1", Year.of(2010),
                        new AuthorDto(1, "Ivan", "Ivanov"), new GenreDto(1, "Horror")),
                new BookDto(2, "Book2", Year.of(2000),
                        new AuthorDto(1, "Ivan", "Ivanov"), new GenreDto(2, "Fairytale"))
        );
        when(bookService.getAll()).thenReturn(expectedBooks);

        this.mvc.perform(get("/api/book"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(expectedBooks)));
    }

    @Test
    void getShouldReturnBook() throws Exception {
        val expectedBook = new BookDto(1, "Book1", Year.of(2010),
                new AuthorDto(1, "Ivan", "Ivanov"), new GenreDto(1, "Horror"));
        when(bookService.get(1)).thenReturn(expectedBook);

        this.mvc.perform(get("/api/book/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(expectedBook)));
    }

    @Test
    void updateShouldReturnUpdatedBook() throws Exception {
        val expectedBook = new BookDto(1, "New Book Title", Year.of(2010),
                new AuthorDto(1, "Ivan", "Ivanov"), new GenreDto(1, "Horror"));

        when(bookService.modify(1,
                new UpdateBookDto("Old title", Year.of(2000), 2,2)))
                .thenReturn(expectedBook);
        this.mvc.perform(put("/api/book/1")
                .content("{\"title\": \"Old title\", \"year\": 2000, \"authorId\": 2, \"genreId\": 2}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(expectedBook)));
    }

    @Test
    void removeShouldCallRemoveMethod() throws Exception {
        doNothing().when(bookService).remove(anyLong());

        this.mvc.perform(delete("/api/book/1"))
                .andExpect(status().isOk());
        verify(bookService, times(1)).remove(1);
    }

    @Test
    void createShouldReturnCreatedBook() throws Exception {
        val expectedBook = new BookDto(1, "Book Title", Year.of(2010),
                new AuthorDto(1, "Ivan", "Ivanov"), new GenreDto(1, "Horror"));

        when(bookService.create(new CreateBookDto("Book Title", Year.of(2010), 1, 1)))
                .thenReturn(expectedBook);

        this.mvc.perform(post("/api/book")
                        .content("{\"title\": \"Book Title\", \"year\": 2010, \"authorId\": 1, \"genreId\": 1}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(expectedBook)));
    }
}