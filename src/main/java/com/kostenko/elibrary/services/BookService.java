package com.kostenko.elibrary.services;

import com.kostenko.elibrary.models.Book;
import com.kostenko.elibrary.repositories.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Page<Book> findByTitleIsContaining(String search, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCase(search, pageable);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
