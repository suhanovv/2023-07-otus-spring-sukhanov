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
import ru.otus.homework.services.authors.AuthorService;
import ru.otus.homework.services.authors.dto.AuthorDto;


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

    @GetMapping("/author/edit/{authorId}")
    public String edit(@PathVariable("authorId") long id, Model model) {
        var authorDto = authorService.get(id);
        model.addAttribute("author", authorDto);
        return "author/edit";
    }

    @PostMapping("/author/edit")
    public String save(@Valid @ModelAttribute("author") AuthorDto author, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "author/edit";
        }
        authorService.modify(author);
        return "redirect:/author";
    }
}