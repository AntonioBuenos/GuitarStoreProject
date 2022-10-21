package by.smirnov.guitarstoreproject.dto.instock;

import by.smirnov.guitarstoreproject.model.enums.Placement;
import by.smirnov.guitarstoreproject.validation.Enum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import static by.smirnov.guitarstoreproject.validation.ValidationConstants.NOT_NULL_MESSAGE;

@Getter
@Setter
@Schema(description = "Information on good item being in stock for making orders")
public class InstockRequest {

    @Schema(description = "Item in stock placement (STORE or WAREHOUSE)")
    @NotNull(message = NOT_NULL_MESSAGE)
    @Enum(enumClass = Placement.class)
    @Enumerated(EnumType.STRING)
    private Placement placement;
}
