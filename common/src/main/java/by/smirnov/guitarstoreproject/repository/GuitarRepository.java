package by.smirnov.guitarstoreproject.repository;

import by.smirnov.guitarstoreproject.model.Guitar;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface GuitarRepository extends CrudRepository<Guitar, Long>, JpaRepository<Guitar, Long>,
        PagingAndSortingRepository<Guitar, Long> {

    @Query(value = "select avg(p.price) from Guitar p where p.isDeleted = false")
    public Double findByHQLQuery();

}
