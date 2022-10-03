package by.smirnov.guitarstoreproject.dto;

import by.smirnov.guitarstoreproject.model.enums.OrderStatus;

import java.sql.Timestamp;

public class OrderDTO implements ObjectDTO{
    private Long id;
    private String userId;
    private String instockId;
    private String deliveryAddress;
    private Timestamp creationDate;
    private Timestamp modificationDate;
    private Timestamp terminationDate;
    private OrderStatus orderStatus;
}
