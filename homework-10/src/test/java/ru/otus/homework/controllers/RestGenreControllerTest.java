package ru.otus.homework.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.services.genres.GenreService;
import ru.otus.homework.services.genres.dto.GenreDto;
import ru.otus.homework.services.genres.dto.UpdateGenreDto;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestGenreController.class)
class RestGenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService genreService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void listShouldReturnGenresList() throws Exception {
        val expectedGenres = List.of(new GenreDto(1, "Horror"), new GenreDto(2, "Fairytale"));
        when(genreService.getAll()).thenReturn(expectedGenres);

        this.mvc.perform(get("/api/genre"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(expectedGenres)));
    }


    @Test
    void getShouldReturnGenre() throws Exception {
        val excpectedGenre = new GenreDto(1, "Horror");
        when(genreService.get(1)).thenReturn(excpectedGenre);

        this.mvc.perform(get("/api/genre/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(excpectedGenre)));
    }

    @Test
    void updateShouldReturnUpdatedGenre() throws Exception {
        val expectedGenre = new GenreDto(1, "Tech");

        when(genreService.modify(1, new UpdateGenreDto("Horror"))).thenReturn(expectedGenre);

        this.mvc.perform(put("/api/genre/1")
                        .content("{\"name\": \"Horror\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(expectedGenre)));
    }
}