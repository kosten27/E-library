package com.kostenko.elibrary.services;

import com.kostenko.elibrary.models.BookOrder;
import com.kostenko.elibrary.repositories.BookOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BookOrderService {

    @Autowired
    private BookOrderRepository bookOrderRepository;

    public Page<BookOrder> findPagination(Pageable pageable) {
        return bookOrderRepository.findAll(pageable);
    }

    public BookOrder save(BookOrder bookOrder) {
        if (bookOrder.getDate() == null) {
            bookOrder.setDate(new Date());
        }
        return bookOrderRepository.save(bookOrder);
    }

    public BookOrder findById(Long id) {
        return bookOrderRepository.findById(id).orElse(null);
    }

}
