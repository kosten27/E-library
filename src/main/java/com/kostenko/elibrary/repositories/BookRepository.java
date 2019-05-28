package com.kostenko.elibrary.repositories;

import com.kostenko.elibrary.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

//    @Query("SELECT b, COUNT(s) FROM BOOK b LEFT JOIN Series s " +
//            "WHERE s.inStock = true AND s NOT IN " +
//            "(SELECT DISTINCT bol.series FROM BookOrderLine bol " +
//            "INNER JOIN BookOrder bo " +
//            "ON (bol.bookOrder = bo " +
//            "AND bo.orderStatus IN (com.kostenko.elibrary.models.OrderStatus.RESERVED, " +
//            "com.kostenko.elibrary.models.OrderStatus.COMPLETED))) " +
//            "GROUP BY b")
//    public Map<Book, Integer> findAllAndCount();

    public Page<Book> findByTitleContainingIgnoreCase(String search, Pageable pageable);

}
