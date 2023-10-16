package ru.otus.homework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotFoundController {

    @GetMapping("/404")
    public String get() {
        return "404";
    }
}
