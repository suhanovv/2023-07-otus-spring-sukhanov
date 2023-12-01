package ru.otus.homework.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.services.authors.AuthorService;
import ru.otus.homework.services.authors.dto.AuthorDto;
import ru.otus.homework.services.authors.dto.UpdateAuthorDto;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(RestAuthorController.class)
class RestAuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void listShouldReturnAuthorsList() throws Exception {
        val authors = List.of(
                        new AuthorDto(1, "Ivan", "Ivanov"),
                        new AuthorDto(2, "Sergey", "Sergeev"));

        when(authorService.getAll()).thenReturn(authors);

        this.mvc.perform(get("/api/author"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(authors)));
    }

    @Test
    void getShouldReturnAuthor() throws Exception {
        val expectedAuthor = new AuthorDto(1, "Ivan", "Ivanov");
        when(authorService.get(1)).thenReturn(expectedAuthor);

        this.mvc.perform(get("/api/author/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(expectedAuthor)));
    }

    @Test
    void updateShouldReturnUpdatedAuthor() throws Exception {
        val expectedAuthor = new AuthorDto(1, "Ivan", "Ivanov");
        when(authorService.modify(1, new UpdateAuthorDto("Ivan", "Petrov"))).thenReturn(expectedAuthor);

        this.mvc.perform(put("/api/author/1")
                        .content("{\"firstName\": \"Ivan\", \"lastName\": \"Petrov\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(expectedAuthor)));
    }
}