package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.domain.Genre;
import by.smirnov.guitarstoreproject.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService{

    private final GenreRepository repository;

    @Override
    public Genre findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Genre> findAll(Pageable pageable) {
        return repository.findAll(pageable).getContent();
    }

    @Transactional
    @Override
    public Genre create(Genre object) {
        return repository.save(object);
    }

    @Transactional
    @Override
    public Genre update(Genre toBeUpdated) {
        return repository.save(toBeUpdated);
    }

    @Transactional
    @Override
    public Genre delete(Long id) {
        Genre toBeDeleted = repository.findById(id).orElse(null);
        if(Objects.isNull(toBeDeleted)) return null;
        toBeDeleted.setIsDeleted(true);
        toBeDeleted.setTerminationDate(Timestamp.valueOf(LocalDateTime.now()));
        return repository.save(toBeDeleted);
    }

    @Transactional
    @Override
    public void hardDelete(Long id){
        repository.deleteById(id);
    }
}
