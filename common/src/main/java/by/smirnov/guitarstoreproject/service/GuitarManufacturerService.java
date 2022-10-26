package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.domain.GuitarManufacturer;
import by.smirnov.guitarstoreproject.repository.GuitarManufacturerRepository;
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
public class GuitarManufacturerService {

    private final GuitarManufacturerRepository repository;

    public GuitarManufacturer findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<GuitarManufacturer> findAll(Pageable pageable) {
        return repository.findByIsDeleted(pageable, false).getContent();
    }

    @Transactional
    public GuitarManufacturer create(GuitarManufacturer object) {
        return repository.save(object);
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
