package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.domain.Order;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService{

    Order findById(Long id);

    List<Order> findAll(Pageable pageable);

    Order create(Order created);

    Order update(Order toBeUpdated);

    Order completeOrder(Long id);

    Order cancelOrder(Long id);

    Order suspendOrder(Long id);

    Order resumeOrder(Long id);

    void hardDelete(Long id);
}
