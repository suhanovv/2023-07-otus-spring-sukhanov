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
import ru.otus.homework.services.authors.AuthorService;
import ru.otus.homework.services.authors.dto.AuthorDto;
import ru.otus.homework.services.authors.exceptions.AuthorNotFoundException;


@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/author")
    public String list(Model model) {
        val authors = authorService.getAll();
        model.addAttribute("authors", authors);
        return "author/list";
    }

    @GetMapping("/author/edit")
    public String edit(@RequestParam("id") long id, Model model) {
        try {
            var authorDto = authorService.get(id);
            model.addAttribute("author", authorDto);
        } catch (AuthorNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "author/edit";
    }

    @PostMapping("/author/edit")
    public String save(@Valid @ModelAttribute("author") AuthorDto author, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "author/edit";
        }
        try {
            authorService.modify(author);
        } catch (AuthorNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return "redirect:/author";
    }
}