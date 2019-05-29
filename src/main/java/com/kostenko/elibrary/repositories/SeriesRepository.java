package com.kostenko.elibrary.repositories;

import com.kostenko.elibrary.models.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeriesRepository extends JpaRepository<Series, Long> {

    @Query("SELECT s FROM Series s " +
            "WHERE s.inStock = true AND s NOT IN " +
            "(SELECT DISTINCT bol.series FROM BookOrderLine bol " +
            "INNER JOIN BookOrder bo " +
            "ON (bol.bookOrder = bo " +
            "AND bo.orderStatus IN (com.kostenko.elibrary.models.OrderStatus.RESERVED, " +
            "com.kostenko.elibrary.models.OrderStatus.COMPLETED)))")
    public List<Series> findAllAvailable();

    @Query("SELECT s FROM Series s " +
            "WHERE s IN :series AND (s.inStock = false OR s IN " +
            "(SELECT DISTINCT bol.series FROM BookOrderLine bol " +
            "INNER JOIN BookOrder bo " +
            "ON (bol.bookOrder = bo " +
            "AND bo.orderStatus IN (com.kostenko.elibrary.models.OrderStatus.RESERVED, " +
            "com.kostenko.elibrary.models.OrderStatus.COMPLETED)) " +
            "AND bo.id <> :excludeBookOrderId))")
    public List<Series> findUnavailable(@Param("series") List<Series> series,
                                        @Param("excludeBookOrderId") Long excludeBookOrderId);

}
