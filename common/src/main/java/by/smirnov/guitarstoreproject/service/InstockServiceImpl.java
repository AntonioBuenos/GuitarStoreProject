package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.domain.Instock;
import by.smirnov.guitarstoreproject.domain.enums.GoodStatus;
import by.smirnov.guitarstoreproject.exceptionhandle.NoSuchEntityException;
import by.smirnov.guitarstoreproject.repository.InstockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstockServiceImpl implements InstockService{

    private final InstockRepository repository;

    @Override
    public Instock findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Instock> findAll(Pageable pageable) {
        return repository
                .findAll(pageable)
                .getContent();
    }

    @Transactional
    @Override
    public Instock create(Instock object) {
        return repository.save(object);
    }

    @Transactional
    @Override
    public Instock update(Instock toBeUpdated, GoodStatus goodStatus) {
        Instock old = repository
                .findById(toBeUpdated.getId())
                .orElseThrow(NoSuchEntityException::new);
        old.setGoodStatus(goodStatus);
        return repository.save(old);
    }

    @Transactional
    @Override
    public Instock update(Instock toBeUpdated){
        return repository.save(toBeUpdated);
    }

    @Transactional
    @Override
    public Instock delete(Long id){
        Instock toBeDeleted = repository
                .findById(id)
                .orElseThrow(NoSuchEntityException::new);
        return update(toBeDeleted, GoodStatus.OUT_OF_STOCK);
    }

    @Transactional
    @Override
    public void hardDelete(Long id){
        repository.deleteById(id);
    }
}
