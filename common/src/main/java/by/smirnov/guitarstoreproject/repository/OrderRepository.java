package by.smirnov.guitarstoreproject.repository;

import by.smirnov.guitarstoreproject.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends CrudRepository<Order, Long>, JpaRepository<Order, Long>,
        PagingAndSortingRepository<Order, Long> {
}
