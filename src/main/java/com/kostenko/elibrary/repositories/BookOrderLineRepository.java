package com.kostenko.elibrary.repositories;

import com.kostenko.elibrary.models.BookOrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookOrderLineRepository extends JpaRepository<BookOrderLine, Long> {
}
