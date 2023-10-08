package ru.otus.homework.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import ru.otus.homework.services.genres.GenreService;
import ru.otus.homework.services.genres.dto.GenreDto;
import ru.otus.homework.services.genres.exceptions.GenreNotFoundException;

@Controller
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/genre")
    public String list(Model model) {
        val genres = genreService.getAll();
        model.addAttribute("genres", genres);
        return "genre/list";
    }

    @GetMapping("/genre/edit")
    public String edit(@RequestParam("id") long id, Model model) {
        try {
            val genreDto = genreService.get(id);
            model.addAttribute("genre", genreDto);
        } catch (GenreNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "genre/edit";
    }

    @PostMapping("/genre/edit")
    public String save(@Valid @ModelAttribute("genre") GenreDto genre, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "genre/edit";
        }
        try {
            genreService.modify(genre);
        } catch (GenreNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return "redirect:/genre";
    }
}