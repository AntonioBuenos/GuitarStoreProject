package by.smirnov.guitarshopproject.dto;

import com.neovisionaries.i18n.CountryCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuitarDTO {

    //Добавить валидацию
    private long id;
    private String typeof;
    private String shape;
    private String series;
    private String model;
    private int stringsQnt;
    private String neck;
    private String bridge;
    private String bodyMaterial;
    private double price;
    private CountryCode prodCountry;
    private long brandId;

}
