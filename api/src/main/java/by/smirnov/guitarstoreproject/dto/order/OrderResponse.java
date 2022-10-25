package by.smirnov.guitarstoreproject.dto.order;

import by.smirnov.guitarstoreproject.dto.instock.InstockResponse;
import by.smirnov.guitarstoreproject.dto.user.UserResponse;
import by.smirnov.guitarstoreproject.model.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Timestamp;

@Data
@Schema(description = "User's order information")
public class OrderResponse {

    @Schema(description = "Order identification number")
    private Long id;

    @Schema(description = "User being customer for this order")
    @JsonIgnoreProperties("orders")
    private UserResponse customer;

    @Schema(description = "Ordered item in stock")
    @JsonIgnoreProperties("order")
    private InstockResponse instock;

    @Schema(description = "User's delivery address (may be not the same as user residential address)")
    private String deliveryAddress;

    @Schema(description = "Date/time of order creation")
    private Timestamp creationDate;

    @Schema(description = "Order status (CREATED, CANCELLED, SUSPENDED or COMPLETED)")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
