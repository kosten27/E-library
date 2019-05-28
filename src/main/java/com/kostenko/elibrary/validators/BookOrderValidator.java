package com.kostenko.elibrary.validators;

import com.kostenko.elibrary.exceptions.ValidationException;
import com.kostenko.elibrary.models.BookOrder;
import com.kostenko.elibrary.models.Series;
import com.kostenko.elibrary.services.BookOrderService;
import com.kostenko.elibrary.services.SeriesService;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.util.List;

@Component
public class BookOrderValidator {

    private SeriesService seriesService;

    public BookOrderValidator(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    public void validateAvailableSeries(BookOrder bookOrder) throws ValidationException {
        if (bookOrder.getId() != null) {
            List<Series> unavailableSeries = seriesService.findUnavailableSeries(bookOrder);
            if (unavailableSeries != null && unavailableSeries.size() > 0) {
                System.out.println("The order contains an unavailable book series: " +
                        unavailableSeries.get(0));
                throw new ValidationException("The order contains an unavailable book series: " +
                        unavailableSeries.get(0));
            }
        }

    }
}
