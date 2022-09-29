package by.smirnov.guitarstoreproject.dto;

import com.neovisionaries.i18n.CountryCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuitarManufacturerDTO {
    private Long id;
    private String brand;
    private String company;
    private CountryCode originCountry;
}
