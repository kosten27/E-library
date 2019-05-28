package com.kostenko.elibrary.services;

import com.kostenko.elibrary.exceptions.ValidationException;
import com.kostenko.elibrary.models.BookOrder;
import com.kostenko.elibrary.models.Series;
import com.kostenko.elibrary.repositories.BookOrderRepository;
import com.kostenko.elibrary.validators.BookOrderValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookOrderService {

    private BookOrderRepository bookOrderRepository;
    private SeriesService seriesService;
    private BookOrderValidator bookOrderValidator;

    public BookOrderService(BookOrderRepository bookOrderRepository, SeriesService seriesService,
                            BookOrderValidator bookOrderValidator) {
        this.bookOrderRepository = bookOrderRepository;
        this.seriesService = seriesService;
        this.bookOrderValidator = bookOrderValidator;
    }

    public Page<BookOrder> findPagination(Pageable pageable) {
        return bookOrderRepository.findAll(pageable);
    }

    public List<BookOrder> findBookOrderBySeries(Series series) {
        return bookOrderRepository.findBookOrderBySeries(series);
    }

    public Page<BookOrder> findByDeadlineIsLessThanEqual(Date deadline, Pageable pageable) {
        return bookOrderRepository.findAllByDeadlineLessThanEqual(deadline, pageable);
    }

    public BookOrder save(BookOrder bookOrder) throws ValidationException {
        if (bookOrder.getDate() == null) {
            bookOrder.setDate(new Date());
        }
        bookOrderValidator.validateAvailableSeries(bookOrder);
        return bookOrderRepository.save(bookOrder);
    }

    public BookOrder findById(Long id) {
        return bookOrderRepository.findById(id).orElse(null);
    }

}
