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
import ru.otus.homework.services.books.BookService;
import ru.otus.homework.services.books.dto.CreateBookDto;
import ru.otus.homework.services.books.dto.UpdateBookDto;
import ru.otus.homework.services.genres.GenreService;


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

    @GetMapping("/book/edit/{id}")
    public String edit(@PathVariable("id") long id, Model model) {
        val book = bookService.get(id);
        model.addAttribute("book", UpdateBookDto.toDto(book));

        val genres = genreService.getAll();
        val authors = authorService.getAll();
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);

        return "book/edit";
    }

    @PostMapping("/book/edit/{id}")
    public String editSave(
            @Valid @ModelAttribute("book") UpdateBookDto book,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            val genres = genreService.getAll();
            val authors = authorService.getAll();
            model.addAttribute("genres", genres);
            model.addAttribute("authors", authors);
            model.addAttribute("book", book);
            return "book/edit";
        }
        bookService.modify(book);

        return "redirect:/book";
    }

    @GetMapping("/book/create")
    public String create(Model model) {
        val genres = genreService.getAll();
        val authors = authorService.getAll();
        model.addAttribute("genres", genres);
        model.addAttribute("authors", authors);
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
            model.addAttribute("book", book);
            return "book/create";
        }

        bookService.create(book);

        return "redirect:/book";
    }

    @PostMapping("/book/remove/{bookId}")
    public String remove(@PathVariable("bookId") long id) {
        bookService.remove(id);
        return "redirect:/book";
    }
}