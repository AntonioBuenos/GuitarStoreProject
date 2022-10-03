package by.smirnov.guitarstoreproject.dto;

import by.smirnov.guitarstoreproject.model.Guitar;
import com.neovisionaries.i18n.CountryCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GuitarManufacturerDTO implements ObjectDTO{
    private Long id;
    private String brand;
    private String company;
    private CountryCode originCountry;
    private List<Guitar> guitars;
}
