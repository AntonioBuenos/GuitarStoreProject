package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.model.GuitarManufacturer;
import by.smirnov.guitarstoreproject.repository.GuitarManufacturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GuitarManufacturerService {

    private final GuitarManufacturerRepository repository;

    public GuitarManufacturer findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<GuitarManufacturer> findAll(int pageNumber, int pageSize) {
        return repository.findByIsDeletedOrderById(PageRequest.of(pageNumber, pageSize), false).getContent();
    }

    @Transactional
    public void create(GuitarManufacturer object) {
        repository.save(object);
    }

    @Transactional
    public GuitarManufacturer update(GuitarManufacturer toBeUpdated) {
        return repository.save(toBeUpdated);
    }

    @Transactional
    public GuitarManufacturer delete(Long id) {
        GuitarManufacturer toBeDeleted = repository.findById(id).orElse(null);
        if(Objects.isNull(toBeDeleted)) return null;
        toBeDeleted.setIsDeleted(true);
        toBeDeleted.setTerminationDate(Timestamp.valueOf(LocalDateTime.now()));
        return repository.save(toBeDeleted);
    }

    @Transactional
    public void hardDelete(Long id){
        repository.deleteById(id);
    }

}
