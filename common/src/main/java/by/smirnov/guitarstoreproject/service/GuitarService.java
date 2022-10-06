package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.model.Guitar;
import by.smirnov.guitarstoreproject.repository.GuitarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GuitarService {

    private final GuitarRepository repository;

    public Guitar findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Guitar> findAll() {
        return repository.findByIsDeletedOrderById(false);
    }

    public List<Guitar> findAll(int limit, int offset) {
        return repository.findByIsDeletedOrderById(false);
    }

    public void create(Guitar object) {
        repository.save(object);
    }

    //add 'isDeleted' check and forbid update deleted + message
    public Guitar update(Guitar toBeUpdated) {
        Guitar old = repository.findById(toBeUpdated.getId()).orElse(null);
        toBeUpdated.setCreationDate(old.getCreationDate());
        toBeUpdated.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        toBeUpdated.setManufacturer(old.getManufacturer());
        toBeUpdated.setGuitarGenres(old.getGuitarGenres());
        toBeUpdated.setInstockGuitars(old.getInstockGuitars());
        return repository.save(toBeUpdated);
    }

    //add !=null check + message for cannot be deleted
    public void delete(Long id) {
        Guitar toBeDeleted = repository.findById(id).orElse(null);
        toBeDeleted.setIsDeleted(true);
        toBeDeleted.setTerminationDate(Timestamp.valueOf(LocalDateTime.now()));
        repository.save(toBeDeleted);
    }

    //add !=null check + message for cannot be deleted
    public void hardDelete(Long id){
        //Guitar toBeHardDeleted = guitarRepo.findById(id).orElse(null);
        repository.deleteById(id);
    }

    public String showAverageGuitarPrice() {
        return String.format("%.2f", repository.findByHQLQuery()) + "$";
    }
}
