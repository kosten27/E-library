package com.kostenko.elibrary.repositories;

import com.kostenko.elibrary.models.BookOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookOrderRepository extends JpaRepository<BookOrder, Long> {
}
