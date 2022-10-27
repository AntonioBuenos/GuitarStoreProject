package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.domain.Guitar;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface GuitarService {

    Guitar findById(Long id);

    List<Guitar> findAll(Pageable pageable);

    Guitar create(Guitar object);

    Guitar update(Guitar toBeUpdated);

    Guitar delete(Long id);

    void hardDelete(Long id);

    Map<String, String> showAverageGuitarPrices();
}
