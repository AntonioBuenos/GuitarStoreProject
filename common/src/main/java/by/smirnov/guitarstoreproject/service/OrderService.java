package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.model.Order;
import by.smirnov.guitarstoreproject.model.enums.OrderStatus;
import by.smirnov.guitarstoreproject.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;

    public Order findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Order> findAll() {
        return repository.findAll();
    }

    public List<Order> findAll(int limit, int offset) {
        return repository.findAll();
    }

    public void create(Order object) {
        object.setOrderStatus(OrderStatus.CREATED);
        // change good_status to reserved
        repository.save(object);
    }

    public Order update(Order toBeUpdated) {
        Order old = repository.getReferenceById(toBeUpdated.getId());
        toBeUpdated.setCreationDate(old.getCreationDate());
        toBeUpdated.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        toBeUpdated.setOrderStatus(old.getOrderStatus());
        toBeUpdated.setCustomer(old.getCustomer());
        toBeUpdated.setInstock(old.getInstock());
        return repository.save(toBeUpdated);
    }

    public void completeOrder(Long id) {
        Order toBeCompleted = repository.findById(id).orElse(null);
        toBeCompleted.setOrderStatus(OrderStatus.COMPLETED);
        toBeCompleted.setTerminationDate(Timestamp.valueOf(LocalDateTime.now()));
        repository.save(toBeCompleted);
    }

    public void cancelOrder(Long id) {
        Order toBeCanceled = repository.findById(id).orElse(null);
        toBeCanceled.setOrderStatus(OrderStatus.CANCELLED);
        //change good_status
        toBeCanceled.setTerminationDate(Timestamp.valueOf(LocalDateTime.now()));
        repository.save(toBeCanceled);
    }

    public void suspendOrder(Long id) {
        Order toBeSuspended = repository.findById(id).orElse(null);
        toBeSuspended.setOrderStatus(OrderStatus.SUSPENDED);
        toBeSuspended.setTerminationDate(Timestamp.valueOf(LocalDateTime.now()));
        repository.save(toBeSuspended);
    }

    public void hardDelete(Long id){
        repository.deleteById(id);
    }
}
