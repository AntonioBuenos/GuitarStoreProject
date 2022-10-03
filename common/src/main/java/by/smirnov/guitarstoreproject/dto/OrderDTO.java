package by.smirnov.guitarstoreproject.dto;

import by.smirnov.guitarstoreproject.model.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class OrderDTO implements ObjectDTO{
    private Long id;
    private String userId;
    private String instockId;
    private String deliveryAddress;
    private Timestamp creationDate;
    private OrderStatus orderStatus;
}
