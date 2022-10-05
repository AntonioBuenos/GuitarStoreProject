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

    public void create(Instock object, Long guitarId) {
        object.setGoodStatus(GoodStatus.AVAILABLE);
        object.setGuitarPosition(guitarService.findById(guitarId));
        repository.save(object);
    }

    public Instock update(Instock toBeUpdated, GoodStatus goodStatus) {
        Instock old = repository.getReferenceById(toBeUpdated.getId());
        toBeUpdated.setCreationDate(old.getCreationDate());
        toBeUpdated.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        toBeUpdated.setGoodStatus(goodStatus);
        toBeUpdated.setGuitarPosition(old.getGuitarPosition());
        toBeUpdated.setOrder(old.getOrder());
        return repository.save(toBeUpdated);
    }

    public Instock update(Instock toBeUpdated){
        Instock old = repository.getReferenceById(toBeUpdated.getId());
        GoodStatus goodStatus = old.getGoodStatus();
        return update(toBeUpdated, goodStatus);
    }

    public void hardDelete(Long id){
        repository.deleteById(id);
    }
}
