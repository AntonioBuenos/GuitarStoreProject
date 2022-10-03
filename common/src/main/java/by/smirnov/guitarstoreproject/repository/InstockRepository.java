package by.smirnov.guitarstoreproject.repository;

import by.smirnov.guitarstoreproject.model.Instock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InstockRepository extends CrudRepository<Instock, Long>, JpaRepository<Instock, Long>,
        PagingAndSortingRepository<Instock, Long> {
}
