package com.kostenko.elibrary.repositories;

import com.kostenko.elibrary.models.BookOrder;
import com.kostenko.elibrary.models.OrderStatus;
import com.kostenko.elibrary.models.Reader;
import com.kostenko.elibrary.models.Series;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookOrderRepository extends JpaRepository<BookOrder, Long> {

    @Query("SELECT DISTINCT bol.bookOrder, bol.bookOrder.id FROM BookOrderLine bol " +
            "WHERE bol.series = :series ORDER BY bol.bookOrder.id DESC")
    public List<BookOrder> findBookOrderBySeries(@Param("series") Series series);

    public Page<BookOrder> findAllByDeadlineLessThanEqualAndOrderStatusEquals(Date deadline,
                                                                               Pageable pageable,
                                                                               OrderStatus orderStatus);

    public boolean existsAllByReaderEqualsAndDeadlineLessThanEqualAndOrderStatusIn(Reader reader,
                                                                                  Date deadline,
                                                                                  List<OrderStatus> orderStatuses);
}
