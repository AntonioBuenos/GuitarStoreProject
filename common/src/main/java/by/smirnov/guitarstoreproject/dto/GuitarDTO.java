package by.smirnov.guitarstoreproject.dto;

import by.smirnov.guitarstoreproject.model.Genre;
import by.smirnov.guitarstoreproject.model.GuitarManufacturer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.neovisionaries.i18n.CountryCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GuitarDTO {

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
    private GuitarManufacturer manufacturer;
    private List<Genre> guitarGenres;
}
