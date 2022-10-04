package by.smirnov.guitarstoreproject.dto;

import by.smirnov.guitarstoreproject.model.User;
import by.smirnov.guitarstoreproject.model.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class OrderDTO implements ObjectDTO{
    private Long id;

    @JsonBackReference
    private UserDTO customer;

    private Long instockId;
    private String deliveryAddress;
    private Timestamp creationDate;
    private OrderStatus orderStatus;
}
