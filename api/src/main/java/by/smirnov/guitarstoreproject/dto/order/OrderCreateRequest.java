package by.smirnov.guitarstoreproject.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static by.smirnov.guitarstoreproject.validation.ValidationConstants.NOT_NULL_MESSAGE;

@Data()
@Schema(description = "User's order information")
public class OrderCreateRequest extends OrderChangeRequest{

    @Schema(description = "Ordered item in stock identification number")
    @NotNull(message = NOT_NULL_MESSAGE)
    private Long instockId;
}
