package ru.otus.homework.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.services.authors.AuthorService;
import ru.otus.homework.services.authors.dto.AuthorDto;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/api/author")
    public List<AuthorDto> list() {
        return authorService.getAll();
    }

    @GetMapping("/api/author/{authorId}")
    public AuthorDto list(@PathVariable("authorId") long id) {
        return authorService.get(id);
    }
//
//    @GetMapping("/api/author/edit/{authorId}")
//    public String edit(@PathVariable("authorId") long id, Model model) {
//        var authorDto = authorService.get(id);
//        model.addAttribute("author", authorDto);
//        return "author/edit";
//    }
//
//    @PostMapping("/api/author/edit")
//    public String save(@Valid @ModelAttribute("author") AuthorDto author, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "author/edit";
//        }
//        authorService.modify(author);
//        return "redirect:/author";
//    }
}