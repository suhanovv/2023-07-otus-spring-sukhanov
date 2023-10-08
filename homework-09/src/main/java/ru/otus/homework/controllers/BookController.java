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
import ru.otus.homework.services.books.BookService;
import ru.otus.homework.services.books.dto.CreateBookDto;
import ru.otus.homework.services.books.dto.UpdateBookDto;
import ru.otus.homework.services.books.exceptions.BookNotFoundException;
import ru.otus.homework.services.books.exceptions.CreateBookException;
import ru.otus.homework.services.books.exceptions.ModifyBookException;
import ru.otus.homework.services.genres.GenreService;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping("/book")
    public String list(Model model) {
        val books = bookService.getAll();
        model.addAttribute("books", books);
        return "book/list";
    }

    @GetMapping("/book/edit")
    public String edit(@RequestParam("id") long id, Model model) {
        try {
            val book = bookService.get(id);
            model.addAttribute("book", UpdateBookDto.toDto(book));
        } catch (BookNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        val genres = genreService.getAll();
        val authors = authorService.getAll();
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);
        model.addAttribute("years", getInitialRangeOfYears());

        return "book/edit";
    }

    @PostMapping("/book/edit")
    public String editSave(
            @Valid @ModelAttribute("book") UpdateBookDto book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            val genres = genreService.getAll();
            val authors = authorService.getAll();
            model.addAttribute("genres", genres);
            model.addAttribute("authors", authors);
            model.addAttribute("years", getInitialRangeOfYears());
            model.addAttribute("book", book);
            return "book/edit";
        }
        try {
            bookService.modify(book);
        } catch (BookNotFoundException | ModifyBookException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return "redirect:/book";
    }

    @GetMapping("/book/create")
    public String create(Model model) {
        val genres = genreService.getAll();
        val authors = authorService.getAll();
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);
        model.addAttribute("years", getInitialRangeOfYears());
        model.addAttribute("book",new CreateBookDto());

        return "book/create";
    }

    @PostMapping("/book/create")
    public String createSave(
            @Valid @ModelAttribute("book") CreateBookDto book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            val genres = genreService.getAll();
            val authors = authorService.getAll();
            model.addAttribute("genres", genres);
            model.addAttribute("authors", authors);
            model.addAttribute("years", getInitialRangeOfYears());
            model.addAttribute("book", book);
            return "book/create";
        }
        try {
            bookService.create(book);
        } catch (CreateBookException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return "redirect:/book";
    }

    @PostMapping("/book/remove")
    public String remove(@RequestParam("id") long id, Model model) {
        bookService.remove(id);
        return "redirect:/book";
    }

    private List<Year> getInitialRangeOfYears() {
        val years = new ArrayList<Year>();
        for (int i = 1980; i <= 2030; i++) {
            years.add(Year.of(i));
        }
        return years;
    }

}