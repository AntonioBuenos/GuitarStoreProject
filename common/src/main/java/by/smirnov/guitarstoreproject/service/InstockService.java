package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.domain.Instock;
import by.smirnov.guitarstoreproject.domain.enums.GoodStatus;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InstockService {

    Instock findById(Long id);

    List<Instock> findAll(Pageable pageable);

    Instock create(Instock object);

    Instock update(Instock toBeUpdated, GoodStatus goodStatus);

    Instock update(Instock toBeUpdated);

    Instock delete(Long id);

    void hardDelete(Long id);
}
