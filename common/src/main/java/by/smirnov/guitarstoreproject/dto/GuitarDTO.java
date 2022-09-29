package by.smirnov.guitarstoreproject.dto;

import com.neovisionaries.i18n.CountryCode;
import lombok.Getter;
import lombok.Setter;

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

}
