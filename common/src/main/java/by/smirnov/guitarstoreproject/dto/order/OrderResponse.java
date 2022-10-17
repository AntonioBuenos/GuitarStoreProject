package by.smirnov.guitarstoreproject.dto.order;

import by.smirnov.guitarstoreproject.dto.InstockDTO;
import by.smirnov.guitarstoreproject.dto.UserDTO;
import by.smirnov.guitarstoreproject.model.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
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
import static by.smirnov.guitarstoreproject.validation.ValidationConstants.NULL_MESSAGE;

@Data
@Schema(description = "User's order information")
public class OrderResponse {

    @Schema(description = "Order identification number")
    @Null(message = NULL_MESSAGE)
    private Long id;

    @Schema(description = "User being customer for this order")
    @JsonIgnoreProperties("orders")
    private UserDTO customer;

    @Schema(description = "Ordered item in stock")
    @JsonIgnoreProperties("order")
    private InstockDTO instock;

    @Schema(description = "User's delivery address (may be not the same as user residential address)")
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min = STANDARD_MIN_SIZE, max=ADDRESS_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String deliveryAddress;

    @Schema(description = "Date/time of order creation")
    @Null(message = NULL_MESSAGE)
    private Timestamp creationDate;

    @Schema(description = "Order status (CREATED, CANCELLED, SUSPENDED or COMPLETED)")
    @Null(message = NULL_MESSAGE)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
