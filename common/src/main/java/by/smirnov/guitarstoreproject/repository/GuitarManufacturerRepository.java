package by.smirnov.guitarstoreproject.repository;

import by.smirnov.guitarstoreproject.domain.GuitarManufacturer;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface GuitarManufacturerRepository extends
        CrudRepository<GuitarManufacturer, Long>,
        JpaRepository<GuitarManufacturer, Long> {

    @Cacheable("guitarManufacturer")
    Page<GuitarManufacturer> findByIsDeleted(Pageable pageable, boolean isDeleted);
}
