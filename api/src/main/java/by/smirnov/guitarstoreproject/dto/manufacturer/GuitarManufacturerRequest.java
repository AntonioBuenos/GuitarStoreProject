package by.smirnov.guitarstoreproject.dto.manufacturer;

import by.smirnov.guitarstoreproject.validation.EnumValid;
import com.neovisionaries.i18n.CountryCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static by.smirnov.guitarstoreproject.validation.ValidationConstants.*;

@Getter
@Setter
@Schema(description = "Guitar brand and brand holder company information")
public class GuitarManufacturerRequest {

    @Schema(description = "Unique brand name")
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=STANDARD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String brand;

    @Schema(description = "Name of company that possess the guitar brand")
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max= EXTENDED_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String company;

    @Schema(description = "Company resident country, input by letter code")
    @NotNull(message = NOT_NULL_MESSAGE)
    @EnumValid(enumClass = CountryCode.class)
    private String originCountry;
}
