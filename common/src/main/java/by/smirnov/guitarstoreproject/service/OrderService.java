package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.model.Instock;
import by.smirnov.guitarstoreproject.model.Order;
import by.smirnov.guitarstoreproject.model.User;
import by.smirnov.guitarstoreproject.model.enums.GoodStatus;
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
    private final InstockService instockService;
    private final UserService userService;

    public Order findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Order> findAll() {
        return repository.findAll();
    }

    public List<Order> findAll(int limit, int offset) {
        return repository.findAll();
    }

    public Order save(Order object, Long userId, Long instockId) {
        object.setOrderStatus(OrderStatus.CREATED);
        User customer = userService.findById(userId);
        Instock instockOrdered = instockService.findById(instockId);
        object.setCustomer(customer);
        object.setInstock(instockOrdered);
        return repository.save(object);
    }

    public Order save(Order object) {
        object.setOrderStatus(OrderStatus.CREATED);
        return repository.save(object);
    }

    public void create(Order object){
        object.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        Order createdOrder = save(object);
        instockService.update(createdOrder.getInstock(), GoodStatus.RESERVED);
    }

    public Order update(Order toBeUpdated) {
        Order old = repository.findById(toBeUpdated.getId()).orElse(null);
        toBeUpdated.setCreationDate(old.getCreationDate());
        toBeUpdated.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        toBeUpdated.setOrderStatus(old.getOrderStatus());
        toBeUpdated.setCustomer(old.getCustomer());
        toBeUpdated.setInstock(old.getInstock());
        return repository.save(toBeUpdated);
    }

    public Order completeOrder(Long id) {
        Order orderComplete = repository.findById(id).orElse(null);
        orderComplete.setOrderStatus(OrderStatus.COMPLETED);
        orderComplete.setTerminationDate(Timestamp.valueOf(LocalDateTime.now()));
        instockService.update(orderComplete.getInstock(), GoodStatus.SOLD);
        return repository.save(orderComplete);
    }

    public Order cancelOrder(Long id) {
        Order toBeCanceled = repository.findById(id).orElse(null);
        toBeCanceled.setOrderStatus(OrderStatus.CANCELLED);
        instockService.update(toBeCanceled.getInstock(), GoodStatus.AVAILABLE);
        toBeCanceled.setTerminationDate(Timestamp.valueOf(LocalDateTime.now()));
        return repository.save(toBeCanceled);
    }

    public Order suspendOrder(Long id) {
        Order toBeSuspended = repository.findById(id).orElse(null);
        toBeSuspended.setOrderStatus(OrderStatus.SUSPENDED);
        toBeSuspended.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        return repository.save(toBeSuspended);
    }

    public Order resumeOrder(Long id) {
        Order toBeResumed = repository.findById(id).orElse(null);
        toBeResumed.setOrderStatus(OrderStatus.CREATED);
        toBeResumed.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        return repository.save(toBeResumed);
    }

    public void hardDelete(Long id){
        repository.deleteById(id);
    }
}
