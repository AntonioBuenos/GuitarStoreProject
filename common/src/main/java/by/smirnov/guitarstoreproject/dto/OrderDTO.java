package by.smirnov.guitarstoreproject.dto;

import by.smirnov.guitarstoreproject.model.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
@EqualsAndHashCode
public class OrderDTO implements ObjectDTO{
    private Long id;

    @JsonIgnoreProperties("orders")
    private UserDTO customer;

    @JsonIgnoreProperties("order")
    private InstockDTO instock;

    private String deliveryAddress;
    private Timestamp creationDate;
    private OrderStatus orderStatus;
}
