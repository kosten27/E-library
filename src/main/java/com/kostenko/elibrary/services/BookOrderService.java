package com.kostenko.elibrary.services;

import com.kostenko.elibrary.exceptions.ValidationException;
import com.kostenko.elibrary.models.BookOrder;
import com.kostenko.elibrary.models.OrderStatus;
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

    public Page<BookOrder> findAllByDeadlineLessThanEqualAndOrderStatusEquals(Date deadline,
                                                                              Pageable pageable,
                                                                              OrderStatus orderStatus) {
        return bookOrderRepository.findAllByDeadlineLessThanEqualAndOrderStatusEquals(deadline,
                pageable, orderStatus);
    }

    public BookOrder save(BookOrder bookOrder) throws ValidationException {
        if (bookOrder.getDate() == null) {
            bookOrder.setDate(new Date());
        }
        bookOrderValidator.validateAvailableSeries(bookOrder);
        if (bookOrder.getOrderStatus() == OrderStatus.RESERVED || bookOrder.getOrderStatus() == OrderStatus.COMPLETED) {
            bookOrderValidator.checkUnreturnedOrders(bookOrder);
        }
        return bookOrderRepository.save(bookOrder);
    }

    public BookOrder findById(Long id) {
        return bookOrderRepository.findById(id).orElse(null);
    }

}
