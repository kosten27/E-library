package com.kostenko.elibrary.services;

import com.kostenko.elibrary.models.BookOrderLine;
import com.kostenko.elibrary.models.Series;
import com.kostenko.elibrary.repositories.BookOrderLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookOrderLineService {

    @Autowired
    private BookOrderLineRepository bookOrderLineRepository;


    public BookOrderLine save(BookOrderLine bookOrderLine) {
        return bookOrderLineRepository.save(bookOrderLine);
    }

    public BookOrderLine findById(Long id) {
        return bookOrderLineRepository.findById(id).orElse(null);
    }

    public List<BookOrderLine> findAll() {
        return bookOrderLineRepository.findAll();
    }

    public void deleteById(Long id) {
        bookOrderLineRepository.deleteById(id);
    }

}
