package by.smirnov.guitarstoreproject.dto;

import com.neovisionaries.i18n.CountryCode;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class GuitarManufacturerDTO {
    private long id;
    private String brand;
    private String company;
    private CountryCode originCountry;
    private Timestamp creationDate;
    private Timestamp modificationDate;
    private boolean isDeleted;
    private Timestamp terminationDate;
}
