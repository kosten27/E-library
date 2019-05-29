package com.kostenko.elibrary.controllers;

import com.kostenko.elibrary.models.Author;
import com.kostenko.elibrary.services.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public String getAuthors(Model model,
                             @RequestParam(name = "page", defaultValue = "1") int page,
                             @RequestParam(name = "size", defaultValue = "5") int size) {

        Page<Author> authors = authorService.findPagination(PageRequest.of(page - 1, size));
        model.addAttribute("authors", authors);
        return "authors/list";
    }

    @GetMapping("/authors/add")
    public String showAddAuthor(Author author) {
        return "authors/author";
    }

    @PostMapping("/authors/save")
    public String addAuthor(@Valid Author author, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "authors/author";
        }
        authorService.saveAuthor(author);
        return "redirect:/authors/" + author.getId();
    }

    @GetMapping("/authors/{id}")
    public String showAuthorDetails(@PathVariable("id") long id, Model model) {
        Author author = authorService.find(id);
        model.addAttribute("author", author);
        return "authors/author";
    }
}
