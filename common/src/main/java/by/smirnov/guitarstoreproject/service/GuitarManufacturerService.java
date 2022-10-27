package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.domain.GuitarManufacturer;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GuitarManufacturerService {

    GuitarManufacturer findById(Long id);

    List<GuitarManufacturer> findAll(Pageable pageable);

    GuitarManufacturer create(GuitarManufacturer object);

    GuitarManufacturer update(GuitarManufacturer toBeUpdated);

    GuitarManufacturer delete(Long id);

    void hardDelete(Long id);
}
