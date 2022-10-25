package by.smirnov.guitarstoreproject.dto.manufacturer;

import by.smirnov.guitarstoreproject.dto.guitar.GuitarResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.neovisionaries.i18n.CountryCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Getter
@Setter
@Schema(description = "Guitar brand and brand holder company information")
public class GuitarManufacturerResponse {

    @Schema(description = "Brand identification number")
    private Long id;

    @Schema(description = "Unique brand name")
    private String brand;

    @Schema(description = "Name of company that possess the guitar brand")
    private String company;

    @Schema(description = "Company resident country, input by letter code")
    @Enumerated(EnumType.STRING)
    private CountryCode originCountry;

    @Schema(description = "List of all guitars in price by this brand")
    @JsonIgnoreProperties("manufacturer")
    private List<GuitarResponse> guitars;
}
