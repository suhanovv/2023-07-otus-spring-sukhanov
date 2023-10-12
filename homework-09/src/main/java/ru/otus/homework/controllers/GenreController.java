package ru.otus.homework.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.homework.services.genres.GenreService;
import ru.otus.homework.services.genres.dto.GenreDto;

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

    @GetMapping("/genre/edit/{genreId}")
    public String edit(@PathVariable("genreId") long id, Model model) {
        val genreDto = genreService.get(id);
        model.addAttribute("genre", genreDto);
        return "genre/edit";
    }

    @PostMapping("/genre/edit")
    public String save(@Valid @ModelAttribute("genre") GenreDto genre, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "genre/edit";
        }
        genreService.modify(genre);
        return "redirect:/genre";
    }
}