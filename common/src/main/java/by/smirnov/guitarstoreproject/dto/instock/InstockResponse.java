package by.smirnov.guitarstoreproject.dto.instock;

import by.smirnov.guitarstoreproject.dto.guitar.GuitarResponse;
import by.smirnov.guitarstoreproject.dto.order.OrderResponse;
import by.smirnov.guitarstoreproject.model.enums.GoodStatus;
import by.smirnov.guitarstoreproject.model.enums.Placement;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.sql.Timestamp;

import static by.smirnov.guitarstoreproject.validation.ValidationConstants.NOT_BLANK_MESSAGE;
import static by.smirnov.guitarstoreproject.validation.ValidationConstants.NULL_MESSAGE;

@Getter
@Setter
@Schema(description = "Information on good item being in stock for making orders")
public class InstockResponse {

    @Schema(description = "Item in stock identification number")
    private Long id;

    @Schema(description = "Guitar good position general information related to this item in stock")
    @JsonIgnoreProperties("instockGuitars")
    private GuitarResponse guitarPosition;

    @Schema(description = "Order for this item in stock")
    @JsonIgnoreProperties("instock")
    private OrderResponse order;

    @Schema(description = "Item in stock placement (STORE or WAREHOUSE)")
    @Enumerated(EnumType.STRING)
    private Placement placement;

    @Schema(description = "Item in stock status (AVAILABLE, RESERVED, SOLD or OUT_OF_STOCK)")
    @Enumerated(EnumType.STRING)
    private GoodStatus goodStatus;

    @Schema(description = "Item in stock date/time of being sold")
    private Timestamp dateSold;
}
