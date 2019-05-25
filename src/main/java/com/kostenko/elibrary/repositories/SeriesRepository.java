package com.kostenko.elibrary.repositories;

import com.kostenko.elibrary.models.Series;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository<Series, Long> {
}
