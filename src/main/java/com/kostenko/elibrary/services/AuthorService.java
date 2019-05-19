package com.kostenko.elibrary.services;

import com.kostenko.elibrary.models.Author;
import com.kostenko.elibrary.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public void saveAuthor(Author author) {
        authorRepository.save(author);
    }

    public Author find(long id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            return optionalAuthor.get();
        } else {
            return null;
        }
    }

    public void delete(Author author) {
        authorRepository.delete(author);
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }
}
