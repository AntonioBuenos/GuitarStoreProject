package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.model.Order;
import by.smirnov.guitarstoreproject.model.enums.GoodStatus;
import by.smirnov.guitarstoreproject.model.enums.OrderStatus;
import by.smirnov.guitarstoreproject.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<Order> findAll(int pageNumber, int pageSize, Sort sort) {
        return repository.findAll(PageRequest.of(pageNumber, pageSize, sort)).getContent();
    }

    @Transactional
    public Order save(Order created) {
        instockService.update(created.getInstock(), GoodStatus.RESERVED);
        return repository.save(created);
    }

    @Transactional
    public Order update(Order toBeUpdated) {
        return repository.save(toBeUpdated);
    }

    @Transactional
    public Order completeOrder(Long id) {
        Order orderComplete = repository.findById(id).orElse(null);
        orderComplete.setOrderStatus(OrderStatus.COMPLETED);
        orderComplete.setTerminationDate(Timestamp.valueOf(LocalDateTime.now()));
        instockService.update(orderComplete.getInstock(), GoodStatus.SOLD);
        return repository.save(orderComplete);
    }

    @Transactional
    public Order cancelOrder(Long id) {
        Order toBeCanceled = repository.findById(id).orElse(null);
        toBeCanceled.setOrderStatus(OrderStatus.CANCELLED);
        instockService.update(toBeCanceled.getInstock(), GoodStatus.AVAILABLE);
        toBeCanceled.setTerminationDate(Timestamp.valueOf(LocalDateTime.now()));
        return repository.save(toBeCanceled);
    }

    @Transactional
    public Order suspendOrder(Long id) {
        Order toBeSuspended = repository.findById(id).orElse(null);
        toBeSuspended.setOrderStatus(OrderStatus.SUSPENDED);
        toBeSuspended.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        return repository.save(toBeSuspended);
    }

    @Transactional
    public Order resumeOrder(Long id) {
        Order toBeResumed = repository.findById(id).orElse(null);
        toBeResumed.setOrderStatus(OrderStatus.CREATED);
        toBeResumed.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        return repository.save(toBeResumed);
    }

    @Transactional
    public void hardDelete(Long id){
        repository.deleteById(id);
    }
}
