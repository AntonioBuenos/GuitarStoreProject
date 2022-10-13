package by.smirnov.guitarstoreproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.neovisionaries.i18n.CountryCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Set;

import static by.smirnov.guitarstoreproject.validation.ValidationConstants.*;
import static by.smirnov.guitarstoreproject.validation.ValidationConstants.STANDARD_SIZE_MESSAGE;

@Getter
@Setter
public class GuitarDTO implements ObjectDTO{

    @Null(message = NULL_MESSAGE)
    private Long id;

    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=STANDARD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String typeof;

    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=STANDARD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String shape;

    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=EXTENDED_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String series;

    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=EXTENDED_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String model;

    @Min(1)
    @Max(12)
    private Integer stringsQnt;

    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=STANDARD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String neck;

    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=STANDARD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String bridge;

    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=STANDARD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String bodyMaterial;

    @PositiveOrZero(message = NOT_NEGATIVE_MESSAGE)
    private Double price;

    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Enumerated(EnumType.STRING)
    private CountryCode prodCountry;

    @NotNull(message = NOT_NULL_MESSAGE)
    @JsonIgnoreProperties("guitars")
    private GuitarManufacturerDTO manufacturer;

    @NotNull(message = NOT_NULL_MESSAGE)
    @Null(message = NULL_MESSAGE)
    @JsonIgnoreProperties("byGenreGuitars")
    private Set<GenreDTO> guitarGenres;

    @Null(message = NULL_MESSAGE)
    @JsonIgnoreProperties("guitarPosition")
    private List<InstockDTO> instockGuitars;
}
