package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.domain.Genre;
import by.smirnov.guitarstoreproject.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository repository;

    public Genre findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Genre> findAll(Pageable pageable) {
        return repository.findAll(pageable).getContent();
    }

    public Genre create(Genre object) {
        return repository.save(object);
    }

    public Genre update(Genre toBeUpdated) {
        return repository.save(toBeUpdated);
    }

    public Genre delete(Long id) {
        Genre toBeDeleted = repository.findById(id).orElse(null);
        if(Objects.isNull(toBeDeleted)) return null;
        toBeDeleted.setIsDeleted(true);
        toBeDeleted.setTerminationDate(Timestamp.valueOf(LocalDateTime.now()));
        return repository.save(toBeDeleted);
    }

    public void hardDelete(Long id){
        repository.deleteById(id);
    }
}
