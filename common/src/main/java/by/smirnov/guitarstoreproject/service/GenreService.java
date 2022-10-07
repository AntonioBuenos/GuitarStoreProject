package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.model.Genre;
import by.smirnov.guitarstoreproject.model.enums.Role;
import by.smirnov.guitarstoreproject.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository repository;

    public Genre findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Genre> findAll() {
        return repository.findByIsDeletedOrderById(false);
    }

    public List<Genre> findAll(int limit, int offset) {
        return repository.findByIsDeletedOrderById(false);
    }

    public void create(Genre object) {
        object.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        object.setIsDeleted(false);
        repository.save(object);
    }

    public Genre update(Genre toBeUpdated) {
        Genre old = repository.findById(toBeUpdated.getId()).orElse(null);
        toBeUpdated.setCreationDate(old.getCreationDate());
        toBeUpdated.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        return repository.save(toBeUpdated);
    }

    public void delete(Long id) {
        Genre toBeDeleted = repository.findById(id).orElse(null);
        toBeDeleted.setIsDeleted(true);
        toBeDeleted.setTerminationDate(Timestamp.valueOf(LocalDateTime.now()));
        repository.save(toBeDeleted);
    }

    public void hardDelete(Long id){
        repository.deleteById(id);
    }
}
