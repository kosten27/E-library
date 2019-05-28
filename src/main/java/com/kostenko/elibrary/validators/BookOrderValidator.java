package com.kostenko.elibrary.validators;

import com.kostenko.elibrary.exceptions.ValidationException;
import com.kostenko.elibrary.models.BookOrder;
import com.kostenko.elibrary.models.OrderStatus;
import com.kostenko.elibrary.models.Series;
import com.kostenko.elibrary.repositories.BookOrderRepository;
import com.kostenko.elibrary.services.SeriesService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class BookOrderValidator {

    private BookOrderRepository bookOrderRepository;
    private SeriesService seriesService;

    public BookOrderValidator(BookOrderRepository bookOrderRepository, SeriesService seriesService) {
        this.bookOrderRepository = bookOrderRepository;
        this.seriesService = seriesService;
    }

    public void validateAvailableSeries(BookOrder bookOrder) throws ValidationException {
        if (bookOrder.getId() != null) {
            List<Series> unavailableSeries = seriesService.findUnavailableSeries(bookOrder);
            if (unavailableSeries != null && unavailableSeries.size() > 0) {
                throw new ValidationException("The order contains an unavailable book series: " +
                        unavailableSeries.get(0));
            }
        }
    }

    public void checkUnreturnedOrders(BookOrder bookOrder) throws ValidationException {
        List<OrderStatus> orderStatuses = new ArrayList<>();
        orderStatuses.add(OrderStatus.COMPLETED);
        if (bookOrderRepository.existsAllByReaderEqualsAndDeadlineLessThanEqualAndOrderStatusIn(bookOrder.getReader(),
                new Date(), orderStatuses)) {
            throw new ValidationException("The reader did not return the previous order book.");
        }
    }
}
