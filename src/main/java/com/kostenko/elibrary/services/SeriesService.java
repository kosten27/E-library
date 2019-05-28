package com.kostenko.elibrary.services;

import com.kostenko.elibrary.models.Book;
import com.kostenko.elibrary.models.Series;
import com.kostenko.elibrary.repositories.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriesService {

    @Autowired
    private SeriesRepository seriesRepository;

    public Series save(Series series) {
        return seriesRepository.save(series);
    }

    public Series findById(Long id) {
        return seriesRepository.findById(id).orElse(null);
    }

    public List<Series> findAllAvailable() {
        return  seriesRepository.findAllAvailable();
    }

    public List<Series> findAll() {
        return seriesRepository.findAll();
    }

    public void deleteById(Long id) {
        seriesRepository.deleteById(id);
    }

}
