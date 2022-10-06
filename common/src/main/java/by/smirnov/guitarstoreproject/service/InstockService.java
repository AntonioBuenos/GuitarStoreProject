package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.model.Instock;
import by.smirnov.guitarstoreproject.model.enums.GoodStatus;
import by.smirnov.guitarstoreproject.repository.InstockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstockService {

    private final InstockRepository repository;
    private final GuitarService guitarService;

    public Instock findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Instock> findAll() {
        return repository.findAll();
    }

    public List<Instock> findAll(int limit, int offset) {
        return repository.findAll();
    }

    public void create(Instock object) {
        object.setGoodStatus(GoodStatus.AVAILABLE);
        object.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        repository.save(object);
    }

    public Instock update(Instock toBeUpdated, GoodStatus goodStatus) {
        Instock old = repository.findById(toBeUpdated.getId()).orElse(null);
        toBeUpdated.setCreationDate(old.getCreationDate());
        toBeUpdated.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        toBeUpdated.setGoodStatus(goodStatus);
        toBeUpdated.setGuitarPosition(old.getGuitarPosition());
        toBeUpdated.setOrder(old.getOrder());
        return repository.save(toBeUpdated);
    }

    public Instock update(Instock toBeUpdated){
        Instock old = repository.findById(toBeUpdated.getId()).orElse(null);
        GoodStatus goodStatus = old.getGoodStatus();
        return update(toBeUpdated, goodStatus);
    }

    public Instock delete(Long id){
        return update(repository.findById(id).orElse(null), GoodStatus.OUT_OF_STOCK);
    }

    public void hardDelete(Long id){
        repository.deleteById(id);
    }
}
