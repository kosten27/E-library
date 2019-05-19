package com.kostenko.elibrary.services;

import com.kostenko.elibrary.models.Book;
import com.kostenko.elibrary.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
//    final private List<Book> books;

    public BookService() {
//        books = findAll();
    }

    public Page<Book> findPagination(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Page<Book> findPagination1(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Book> books = findAll();
        List<Book> list;

        if (books.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, books.size());
            list = books.subList(startItem, toIndex);
        }
        return new PageImpl<Book>(list, PageRequest.of(currentPage, pageSize), books.size());
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book findBook(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            return optionalBook.get();
        } else {
            return null;
        }
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
