package by.smirnov.guitarstoreproject.repository;

import by.smirnov.guitarstoreproject.domain.Guitar;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

@Cacheable("guitarManufacturer")
public interface GuitarRepository extends
        CrudRepository<Guitar, Long>,
        JpaRepository<Guitar, Long> {

    @Query(value = "select avg(p.price) from Guitar p where p.isDeleted = false")
    Double findAvgListPrice();

    @Query(value = "select avg(jp.price) from " +
            "(guitarshop.instock i join guitarshop.guitars g on g.id = i.good_id) jp " +
            "where good_status = 'AVAILABLE'", nativeQuery = true)
    Double findAvgInstockPrice();

    Page<Guitar> findByIsDeleted(Pageable pageable, boolean isDeleted);

}
