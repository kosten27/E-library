package com.kostenko.elibrary.services;

import com.kostenko.elibrary.models.Author;
import com.kostenko.elibrary.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Page<Author> findPagination(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    public void saveAuthor(Author author) {
        authorRepository.save(author);
    }

    public Author find(long id) {
        return  authorRepository.findById(id).orElse(null);
    }

    public void delete(Author author) {
        authorRepository.delete(author);
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }
}
