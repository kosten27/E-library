package com.kostenko.elibrary.services;

import com.kostenko.elibrary.models.Book;
import com.kostenko.elibrary.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Page<Book> findPagination(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book findBook(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
