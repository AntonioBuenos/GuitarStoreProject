package by.smirnov.guitarstoreproject.dto;

import by.smirnov.guitarstoreproject.model.Genre;
import by.smirnov.guitarstoreproject.model.GuitarManufacturer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.neovisionaries.i18n.CountryCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Getter
@Setter
/*@ToString(exclude = {"guitarGenres", "instockGuitars"})
@EqualsAndHashCode(exclude = {"guitarGenres", "instockGuitars"})*/
public class GuitarDTO implements ObjectDTO{

    //Добавить валидацию
    private Long id;
    private String typeof;
    private String shape;
    private String series;
    private String model;
    private Integer stringsQnt;
    private String neck;
    private String bridge;
    private String bodyMaterial;
    private Double price;
    private CountryCode prodCountry;
    private Long brandId;

    @JsonBackReference
    private GuitarManufacturerDTO manufacturer;

    @JsonIgnoreProperties("byGenreGuitars")
    private Set<GenreDTO> guitarGenres;

    @JsonManagedReference
    private List<InstockDTO> instockGuitars;
}
