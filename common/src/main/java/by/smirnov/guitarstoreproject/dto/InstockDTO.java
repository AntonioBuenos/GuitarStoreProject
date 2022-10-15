package by.smirnov.guitarstoreproject.dto;

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
public class InstockDTO implements ObjectDTO{

    @Schema(description = "Item in stock identification number")
    @Null(message = NULL_MESSAGE)
    private Long id;

    @Schema(description = "Guitar good position gemeral information related to this item in stock")
    @JsonIgnoreProperties("instockGuitars")
    private GuitarDTO guitarPosition;

    @Schema(description = "Order for this item in stock")
    @JsonIgnoreProperties("instock")
    private OrderDTO order;

    @Schema(description = "Item in stock placement (STORE or WAREHOUSE)")
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Enumerated(EnumType.STRING)
    private Placement placement;

    @Schema(description = "Item in stock status (AVAILABLE, RESERVED, SOLD or OUT_OF_STOCK)")
    @Null(message = NULL_MESSAGE)
    @Enumerated(EnumType.STRING)
    private GoodStatus goodStatus;

    @Schema(description = "Item in stock date/time of being sold")
    @Null(message = NULL_MESSAGE)
    private Timestamp dateSold;
}
