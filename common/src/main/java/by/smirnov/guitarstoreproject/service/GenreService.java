package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.domain.Genre;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenreService {

    Genre findById(Long id);

    List<Genre> findAll(Pageable pageable);

    Genre create(Genre object);

    Genre update(Genre toBeUpdated);

    Genre delete(Long id);

    void hardDelete(Long id);
}
