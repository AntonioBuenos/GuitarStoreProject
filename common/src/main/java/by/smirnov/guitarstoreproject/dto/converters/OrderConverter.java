package by.smirnov.guitarstoreproject.dto.converters;

import by.smirnov.guitarstoreproject.dto.order.OrderChangeRequest;
import by.smirnov.guitarstoreproject.dto.order.OrderCreateRequest;
import by.smirnov.guitarstoreproject.dto.order.OrderResponse;
import by.smirnov.guitarstoreproject.model.Order;
import by.smirnov.guitarstoreproject.model.User;
import by.smirnov.guitarstoreproject.model.enums.OrderStatus;
import by.smirnov.guitarstoreproject.model.enums.Role;
import by.smirnov.guitarstoreproject.service.InstockService;
import by.smirnov.guitarstoreproject.service.OrderService;
import by.smirnov.guitarstoreproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class OrderConverter {

    private final ModelMapper modelMapper;
    private final OrderService service;
    private final InstockService instockService;
    private final UserService userService;

    public Order convert(OrderCreateRequest request, String customerLogin){
        return convert(request, userService.findByLogin(customerLogin));
    }

    public Order convert(OrderCreateRequest request, Long customerId){
        return convert(request, userService.findById(customerId));
    }

    public Order convert(OrderCreateRequest request, User customer){
        Order created = new Order();
        created.setInstock(instockService.findById(request.getInstockId()));
        created.setCustomer(customer);
        created.setOrderStatus(OrderStatus.CREATED);
        created.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        created.setDeliveryAddress(request.getDeliveryAddress());
        return created;
    }

    public Order convert(OrderChangeRequest request, Long orderId){
        Order old = service.findById(orderId);
        old.setDeliveryAddress(request.getDeliveryAddress());
        old.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        return old;
    }

    public OrderResponse convert(Order response){
        return modelMapper.map(response, OrderResponse.class);
    }
}
