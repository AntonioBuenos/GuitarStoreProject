package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.model.Instock;
import by.smirnov.guitarstoreproject.model.enums.GoodStatus;
import by.smirnov.guitarstoreproject.repository.InstockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstockService {

    private final InstockRepository repository;

    public Instock findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Instock> findAll(int pageNumber, int pageSize, Sort sort) {
        return repository.findAll(PageRequest.of(pageNumber, pageSize, sort)).getContent();
    }

    public void create(Instock object) {
        repository.save(object);
    }

    public Instock update(Instock toBeUpdated, GoodStatus goodStatus) {
        Instock old = repository.findById(toBeUpdated.getId()).orElse(null);
        old.setGoodStatus(goodStatus);
        return repository.save(old);
    }

    public Instock update(Instock toBeUpdated){
        return repository.save(toBeUpdated);
    }

    public Instock delete(Long id){
        return update(repository.findById(id).orElse(null), GoodStatus.OUT_OF_STOCK);
    }

    public void hardDelete(Long id){
        repository.deleteById(id);
    }
}
