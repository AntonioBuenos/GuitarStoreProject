package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.model.GuitarManufacturer;
import by.smirnov.guitarstoreproject.repository.GuitarManufacturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GuitarManufacturerService {

    private final GuitarManufacturerRepository repository;

    public GuitarManufacturer findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<GuitarManufacturer> findAll() {
        return repository.findByIsDeletedOrderById(false);
    }

    public List<GuitarManufacturer> findAll(int limit, int offset) {
        //return repository.findAll(PageRequest.of(offset, limit));
        return repository.findByIsDeletedOrderById(false);
    }

    public void create(GuitarManufacturer object) {
        repository.save(object);
    }

    public GuitarManufacturer update(GuitarManufacturer toBeUpdated) {
        GuitarManufacturer old = repository.getReferenceById(toBeUpdated.getId());
        toBeUpdated.setCreationDate(old.getCreationDate());
        toBeUpdated.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        toBeUpdated.setGuitars(old.getGuitars());
        return repository.save(toBeUpdated);
    }

    public void delete(Long id) {
        GuitarManufacturer toBeDeleted = repository.findById(id).orElse(null);
        toBeDeleted.setIsDeleted(true);
        toBeDeleted.setTerminationDate(Timestamp.valueOf(LocalDateTime.now()));
        repository.save(toBeDeleted);
    }

    public void hardDelete(Long id){
        repository.deleteById(id);
    }

}
