package by.smirnov.guitarstoreproject.dto.guitar;

import by.smirnov.guitarstoreproject.dto.genre.GenreResponse;
import by.smirnov.guitarstoreproject.dto.instock.InstockResponse;
import by.smirnov.guitarstoreproject.dto.manufacturer.GuitarManufacturerResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.neovisionaries.i18n.CountryCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Set;

@Getter
@Setter
@Schema(description = "Guitar position: general information and price")
public class GuitarResponse {

    @Schema(description = "Guitar identification number")
    private Long id;

    @Schema(description = "Guitar type (electric, acoustic, bass electric etc.)")
    private String typeof;

    @Schema(description = "Guitar shape")
    private String shape;

    @Schema(description = "Guitar series")
    private String series;

    @Schema(description = "Guitar model")
    private String model;

    @Schema(description = "Guitar strings quantity")
    private Integer stringsQnt;

    @Schema(description = "Guitar  neck set type (Set, Bolt-on, Neck-thru)")
    private String neck;

    @Schema(description = "Guitar bridge type")
    private String bridge;

    @Schema(description = "Guitar body material")
    private String bodyMaterial;

    @Schema(description = "Guitar price, $")
    private Double price;

    @Schema(description = "Guitar made in country information, input by letter code")
    @Enumerated(EnumType.STRING)
    private CountryCode prodCountry;

    @Schema(description = "Guitar brand and brand holder company information")
    @JsonIgnoreProperties("guitars")
    private GuitarManufacturerResponse manufacturer;

    @Schema(description = "List of musical genres this guitar is usually used to play")
    @JsonIgnoreProperties("byGenreGuitars")
    private Set<GenreResponse> guitarGenres;

    @Schema(description = "List of items in stock for this guitar position")
    @JsonIgnoreProperties("guitarPosition")
    private Set<InstockResponse> instockGuitars;
}
