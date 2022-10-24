package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.model.Guitar;
import by.smirnov.guitarstoreproject.repository.GuitarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.smirnov.guitarstoreproject.constants.ControllerConstants.AVG_BY_INSTOCK;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.AVG_BY_PRICELIST;

@Service
@RequiredArgsConstructor
public class GuitarService {

    private final GuitarRepository repository;

    public Guitar findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Guitar> findAll(int pageNumber, int pageSize) {
        return repository.findByIsDeletedOrderById(PageRequest.of(pageNumber, pageSize), false).getContent();
    }

    @Transactional
    public void create(Guitar object) {
        repository.save(object);
    }

    //add 'isDeleted' check and forbid update deleted + message
    @Transactional
    public Guitar update(Guitar toBeUpdated) {
        return repository.save(toBeUpdated);
    }

    //add !=null check + message for cannot be deleted
    @Transactional
    public void delete(Long id) {
        Guitar toBeDeleted = repository.findById(id).orElse(null);
        toBeDeleted.setIsDeleted(true);
        toBeDeleted.setTerminationDate(Timestamp.valueOf(LocalDateTime.now()));
        repository.save(toBeDeleted);
    }

    //add !=null check + message for cannot be deleted
    @Transactional
    public void hardDelete(Long id){
        //Guitar toBeHardDeleted = guitarRepo.findById(id).orElse(null);
        repository.deleteById(id);
    }

    public Map<String, String> showAverageGuitarPrices() {
        Map<String, String> avgResults = new HashMap<>();
        avgResults.put(AVG_BY_PRICELIST, String.format("%.2f", repository.findAvgListPrice()) + "$");
        avgResults.put(AVG_BY_INSTOCK, String.format("%.2f", repository.findAvgInstockPrice()) + "$");
        return avgResults;
    }
}
