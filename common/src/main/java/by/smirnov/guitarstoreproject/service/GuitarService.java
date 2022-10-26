package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.domain.Guitar;
import by.smirnov.guitarstoreproject.repository.GuitarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static by.smirnov.guitarstoreproject.constants.CommonConstants.AVG_BY_INSTOCK;
import static by.smirnov.guitarstoreproject.constants.CommonConstants.AVG_BY_PRICELIST;
import static by.smirnov.guitarstoreproject.constants.CommonConstants.AVG_FORMAT;

@Service
@RequiredArgsConstructor
public class GuitarService {

    private final GuitarRepository repository;

    public Guitar findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Guitar> findAll(Pageable pageable) {
        return repository
                .findByIsDeleted(pageable, false)
                .getContent();
    }

    @Transactional
    public void create(Guitar object) {
        repository.save(object);
    }

    @Transactional
    public Guitar update(Guitar toBeUpdated) {
        return repository.save(toBeUpdated);
    }

    @Transactional
    public Guitar delete(Long id) {
        Guitar toBeDeleted = repository.findById(id).orElse(null);
        if(Objects.isNull(toBeDeleted)) return null;
        toBeDeleted.setIsDeleted(true);
        toBeDeleted.setTerminationDate(Timestamp.valueOf(LocalDateTime.now()));
        return repository.save(toBeDeleted);
    }

    @Transactional
    public void hardDelete(Long id){
        repository.deleteById(id);
    }

    public Map<String, String> showAverageGuitarPrices() {
        Map<String, String> avgResults = new HashMap<>();
        avgResults.put(AVG_BY_PRICELIST, String.format(AVG_FORMAT, repository.findAvgListPrice()));
        avgResults.put(AVG_BY_INSTOCK, String.format(AVG_FORMAT, repository.findAvgInstockPrice()));
        return avgResults;
    }
}
