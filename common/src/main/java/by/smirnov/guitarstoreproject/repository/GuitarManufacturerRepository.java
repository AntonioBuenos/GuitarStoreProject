package by.smirnov.guitarstoreproject.repository;

import by.smirnov.guitarstoreproject.model.GuitarManufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GuitarManufacturerRepository extends CrudRepository<GuitarManufacturer, Long>,
        JpaRepository<GuitarManufacturer, Long>,
        PagingAndSortingRepository<GuitarManufacturer, Long> {

}
