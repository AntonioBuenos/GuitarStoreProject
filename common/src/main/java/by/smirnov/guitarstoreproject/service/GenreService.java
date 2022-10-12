package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.model.Genre;
import by.smirnov.guitarstoreproject.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository repository;

    public Genre findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Genre> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public List<Genre> findAll(int limit, int offset) {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
}
