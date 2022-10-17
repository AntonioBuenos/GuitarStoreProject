package by.smirnov.guitarstoreproject.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "User's order information")
public class OrderCreateRequest extends OrderChangeRequest{

    @Schema(description = "Ordered item in stock identification number")
    private Long instockId;
}
