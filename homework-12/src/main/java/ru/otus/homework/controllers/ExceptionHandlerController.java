package ru.otus.homework.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.homework.services.authors.exceptions.AuthorNotFoundException;
import ru.otus.homework.services.books.exceptions.BookNotFoundException;
import ru.otus.homework.services.genres.exceptions.GenreNotFoundException;


@ControllerAdvice
public class ExceptionHandlerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler({BookNotFoundException.class, AuthorNotFoundException.class, GenreNotFoundException.class})
    public String handleNotFoundError() {
        return "redirect:/404";
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleExceptions(HttpServletRequest request, Exception e) {
        LOGGER.error("Request: " + request.getRequestURL() + " raised " + e);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("500");
        return modelAndView;
    }

}
