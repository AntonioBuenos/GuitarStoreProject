package by.smirnov.guitarstoreproject.dto.guitar;

import by.smirnov.guitarstoreproject.dto.genre.GenreIdRequest;
import by.smirnov.guitarstoreproject.dto.manufacturer.GuitarManufacturerIdRequest;
import by.smirnov.guitarstoreproject.validation.EnumValid;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.neovisionaries.i18n.CountryCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Set;

import static by.smirnov.guitarstoreproject.validation.ValidationConstants.*;

@Getter
@Setter
@Schema(description = "Guitar position: general information and price")
public class GuitarRequest {

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
    @NotNull(message = NOT_NULL_MESSAGE)
    @EnumValid(enumClass = CountryCode.class)
    private String prodCountry;

    @Schema(description = "Guitar brand and brand holder company information")
    @NotNull(message = NOT_NULL_MESSAGE)
    @JsonIgnoreProperties("guitars")
    private GuitarManufacturerIdRequest manufacturer;

    @Schema(description = "List of musical genres this guitar is usually used to play")
    @NotNull(message = NOT_NULL_MESSAGE)
    @JsonIgnoreProperties("byGenreGuitars")
    private Set<GenreIdRequest> guitarGenres;
}
