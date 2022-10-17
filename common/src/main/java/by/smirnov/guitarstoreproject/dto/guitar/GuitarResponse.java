package by.smirnov.guitarstoreproject.dto.guitar;

import by.smirnov.guitarstoreproject.dto.GenreDTO;
import by.smirnov.guitarstoreproject.dto.GuitarManufacturerDTO;
import by.smirnov.guitarstoreproject.dto.InstockDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.neovisionaries.i18n.CountryCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Set;

import static by.smirnov.guitarstoreproject.validation.ValidationConstants.*;
import static by.smirnov.guitarstoreproject.validation.ValidationConstants.NULL_MESSAGE;

@Getter
@Setter
@Schema(description = "Guitar position: general information and price")
public class GuitarResponse {

    @Schema(description = "Guitar identification number")
    @Null(message = NULL_MESSAGE)
    private Long id;

    @Schema(description = "Guitar type (electric, acoustic, bass electric etc.)")
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=STANDARD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String typeof;

    @Schema(description = "Guitar shape")
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=STANDARD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String shape;

    @Schema(description = "Guitar series")
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=EXTENDED_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String series;

    @Schema(description = "Guitar model")
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=EXTENDED_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String model;

    @Schema(description = "Guitar strings quantity")
    @Min(1)
    @Max(12)
    private Integer stringsQnt;

    @Schema(description = "Guitar  neck set type (Set, Bolt-on, Neck-thru)")
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=STANDARD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String neck;

    @Schema(description = "Guitar bridge type")
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=STANDARD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String bridge;

    @Schema(description = "Guitar body material")
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=STANDARD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String bodyMaterial;

    @Schema(description = "Guitar price, $")
    @PositiveOrZero(message = NOT_NEGATIVE_MESSAGE)
    private Double price;

    @Schema(description = "Guitar made in country information, input by letter code")
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Enumerated(EnumType.STRING)
    private CountryCode prodCountry;

    @Schema(description = "Guitar brand and brand holder company information")
    @NotNull(message = NOT_NULL_MESSAGE)
    @JsonIgnoreProperties("guitars")
    private GuitarManufacturerDTO manufacturer;

    @Schema(description = "List of musical genres this guitar is usually used to play")
    @NotNull(message = NOT_NULL_MESSAGE)
    @Null(message = NULL_MESSAGE)
    @JsonIgnoreProperties("byGenreGuitars")
    private Set<GenreDTO> guitarGenres;

    @Schema(description = "List of items in stock for this guitar position")
    @Null(message = NULL_MESSAGE)
    @JsonIgnoreProperties("guitarPosition")
    private List<InstockDTO> instockGuitars;
}