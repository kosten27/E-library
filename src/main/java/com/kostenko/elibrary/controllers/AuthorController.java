package com.kostenko.elibrary.controllers;

import com.kostenko.elibrary.models.Author;
import com.kostenko.elibrary.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/authors")
    public String getAuthors(Model model) {
        List<Author> authors = authorService.findAll();
        model.addAttribute("authors", authors);
        return "authors/list";
    }

    @GetMapping("/authors/add")
    public String showAddAuthor(Author author) {
        return "authors/add";
    }

    @PostMapping("/authors/add")
    public String addAuthor(@Valid Author author, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "authors/add";
        }
        authorService.saveAuthor(author);
        return "redirect:/authors";
    }

    @GetMapping("/authors/update/{id}")
    public String showUpdateAuthor(@PathVariable("id") long id, Model model) {
        Author author = authorService.find(id);
        model.addAttribute("author", author);
        return "authors/update";
    }

    @PostMapping("/authors/update/{id}")
    public String updateAuthor(@PathVariable("id") long id, @Valid Author author, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "authors/update";
        }
        authorService.saveAuthor(author);
        return "redirect:/authors";
    }

    @GetMapping("/authors/delete/{id}")
    public String deleteAuthor(@PathVariable("id") long id) {
        Author author = authorService.find(id);
        authorService.delete(author);
        return "redirect:/authors";
    }
}
