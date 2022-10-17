package by.smirnov.guitarstoreproject.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static by.smirnov.guitarstoreproject.validation.ValidationConstants.*;
import static by.smirnov.guitarstoreproject.validation.ValidationConstants.STANDARD_SIZE_MESSAGE;

@Data
@Schema(description = "User's order information")
public class OrderChangeRequest {

    @Schema(description = "User's delivery address (may be not the same as user residential address)")
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min = STANDARD_MIN_SIZE, max=ADDRESS_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String deliveryAddress;
}
