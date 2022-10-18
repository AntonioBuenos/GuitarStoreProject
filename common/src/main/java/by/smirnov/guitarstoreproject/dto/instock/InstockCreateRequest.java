package by.smirnov.guitarstoreproject.dto.instock;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Information on good item being in stock for making orders")
public class InstockCreateRequest extends InstockRequest{

    @Schema(description = "Guitar good position general information related to this item in stock")
    private Long guitarId;
}
