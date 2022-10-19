package by.smirnov.guitarstoreproject.repository;

import by.smirnov.guitarstoreproject.model.GuitarManufacturer;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface GuitarManufacturerRepository extends CrudRepository<GuitarManufacturer, Long>,
        JpaRepository<GuitarManufacturer, Long> {

    @Cacheable("guitarManufacturer")
    public List<GuitarManufacturer> findByIsDeletedOrderById(boolean isDeleted);
}
