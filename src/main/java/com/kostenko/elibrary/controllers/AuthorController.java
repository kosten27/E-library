package com.kostenko.elibrary.controllers;

import com.kostenko.elibrary.models.Author;
import com.kostenko.elibrary.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;

@Controller
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/authors")
    public String getAuthors(Model model,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Author> authors = authorService.findPagination(PageRequest.of(currentPage - 1, pageSize));
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

    @GetMapping("/authors/{id}/edit")
    public String showUpdateAuthor(@PathVariable("id") long id, Model model) {
        Author author = authorService.find(id);
        model.addAttribute("author", author);
        return "authors/update";
    }

    @PostMapping("/authors/{id}/edit")
    public String updateAuthor(@PathVariable("id") long id, @Valid Author author, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "authors/update";
        }
        authorService.saveAuthor(author);
        return "redirect:/authors/" + author.getId();
    }

    @GetMapping("/authors/{id}")
    public String showAuthorDetails(@PathVariable("id") long id, Model model) {
        Author author = authorService.find(id);
        model.addAttribute("author", author);
        return "authors/authorDetails";
    }

    @GetMapping("/authors/delete/{id}")
    public String deleteAuthor(@PathVariable("id") long id) {
        Author author = authorService.find(id);
        authorService.delete(author);
        return "redirect:/authors";
    }
}
