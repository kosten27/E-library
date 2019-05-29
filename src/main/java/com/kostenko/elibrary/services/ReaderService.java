package com.kostenko.elibrary.services;

import com.kostenko.elibrary.models.Reader;
import com.kostenko.elibrary.repositories.ReaderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderService {

    private ReaderRepository readerRepository;

    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public Page<Reader> findPagination(Pageable pageable) {
        return readerRepository.findAll(pageable);
    }

    public Reader save(Reader reader) {
        return readerRepository.save(reader);
    }

    public Reader findById(Long id) {
        return readerRepository.findById(id).orElse(null);
    }

    public List<Reader> findAll() {
        return readerRepository.findAll();
    }

    public void deleteById(Long id) {
        readerRepository.deleteById(id);
    }
}
