package by.smirnov.guitarstoreproject.dto.manufacturer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Guitar brand and brand holder company information")
public class GuitarManufacturerIdRequest {

    @Schema(description = "Brand identification number")
    private Long id;
}
