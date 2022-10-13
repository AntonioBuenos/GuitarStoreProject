package by.smirnov.guitarstoreproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.neovisionaries.i18n.CountryCode;
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
public class GuitarManufacturerDTO implements ObjectDTO{

    @Null(message = NULL_MESSAGE)
    private Long id;

    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=STANDARD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String brand;

    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max= EXTENDED_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String company;

    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Enumerated(EnumType.STRING)
    private CountryCode originCountry;

    @Null(message = NULL_MESSAGE)
    @JsonIgnoreProperties("manufacturer")
    private List<GuitarDTO> guitars;
}
