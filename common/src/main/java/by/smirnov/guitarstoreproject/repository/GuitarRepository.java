package by.smirnov.guitarstoreproject.repository;

import by.smirnov.guitarstoreproject.model.Guitar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GuitarRepository extends CrudRepository<Guitar, Long>, JpaRepository<Guitar, Long>,
        PagingAndSortingRepository<Guitar, Long> {

    @Query(value = "select avg(p.price) from Guitar p where p.isDeleted = false")
    public Double findByHQLQuery();

}
