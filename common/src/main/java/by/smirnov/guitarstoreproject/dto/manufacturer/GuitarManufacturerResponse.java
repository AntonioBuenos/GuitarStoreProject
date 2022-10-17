package by.smirnov.guitarstoreproject.dto.manufacturer;

import by.smirnov.guitarstoreproject.dto.GuitarDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.neovisionaries.i18n.CountryCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.List;

import static by.smirnov.guitarstoreproject.validation.ValidationConstants.*;

@Getter
@Setter
@Schema(description = "Guitar brand and brand holder company information")
public class GuitarManufacturerResponse {

    @Schema(description = "Brand identification number")
    @Null(message = NULL_MESSAGE)
    private Long id;

    @Schema(description = "Unique brand name")
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=STANDARD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String brand;

    @Schema(description = "Name of company that possess the guitar brand")
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max= EXTENDED_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String company;

    @Schema(description = "Company resident country, input by letter code")
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Enumerated(EnumType.STRING)
    private CountryCode originCountry;

    @Schema(description = "List of all guitars in price by this brand")
    @Null(message = NULL_MESSAGE)
    @JsonIgnoreProperties("manufacturer")
    private List<GuitarDTO> guitars;
}
