package by.smirnov.guitarstoreproject.dto;

import by.smirnov.guitarstoreproject.model.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

import static by.smirnov.guitarstoreproject.validation.ValidationConstants.*;

@Data
@ToString
@EqualsAndHashCode
public class OrderDTO implements ObjectDTO{

    @Null(message = NULL_MESSAGE)
    private Long id;

    @JsonIgnoreProperties("orders")
    private UserDTO customer;

    @JsonIgnoreProperties("order")
    private InstockDTO instock;

    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min = STANDARD_MIN_SIZE, max=ADDRESS_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String deliveryAddress;

    @Null(message = NULL_MESSAGE)
    private Timestamp creationDate;

    @Null(message = NULL_MESSAGE)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
