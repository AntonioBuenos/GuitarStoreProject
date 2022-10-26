package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.domain.Instock;
import by.smirnov.guitarstoreproject.domain.enums.GoodStatus;
import by.smirnov.guitarstoreproject.repository.InstockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InstockService {

    private final InstockRepository repository;

    public Instock findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Instock> findAll(Pageable pageable) {
        return repository
                .findAll(pageable)
                .getContent();
    }

    @Transactional
    public Instock create(Instock object) {
        return repository.save(object);
    }

    @Transactional
    public Instock update(Instock toBeUpdated, GoodStatus goodStatus) {
        Instock old = repository.findById(toBeUpdated.getId()).orElse(null);
        if(Objects.isNull(old)) return null;
        old.setGoodStatus(goodStatus);
        return repository.save(old);
    }

    @Transactional
    public Instock update(Instock toBeUpdated){
        return repository.save(toBeUpdated);
    }

    @Transactional
    public Instock delete(Long id){
        Instock toBeDeleted = repository.findById(id).orElse(null);
        if(Objects.isNull(toBeDeleted)) return null;
        return update(toBeDeleted, GoodStatus.OUT_OF_STOCK);
    }

    @Transactional
    public void hardDelete(Long id){
        repository.deleteById(id);
    }
}
