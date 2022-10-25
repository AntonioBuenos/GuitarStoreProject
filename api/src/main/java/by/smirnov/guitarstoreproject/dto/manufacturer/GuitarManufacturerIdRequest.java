package by.smirnov.guitarstoreproject.dto.manufacturer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@Schema(description = "Guitar brand and brand holder company information")
public class GuitarManufacturerIdRequest {

    @Schema(description = "Brand identification number")
    @Min(1)
    @Max(Long.MAX_VALUE)
    private Long id;
}
