package by.smirnov.guitarstoreproject.builder;

import by.smirnov.guitarstoreproject.domain.GuitarManufacturer;
import com.neovisionaries.i18n.CountryCode;

import static by.smirnov.guitarstoreproject.constants.TestConstants.TEST_DATE_TIME;
import static by.smirnov.guitarstoreproject.constants.TestConstants.TEST_ID;
import static by.smirnov.guitarstoreproject.constants.TestConstants.TEST_STRING;

public class Brands {

    public static GuitarManufacturer.GuitarManufacturerBuilder aBrand(){
        return GuitarManufacturer.builder()
                .id(TEST_ID)
                .brand(TEST_STRING)
                .company(TEST_STRING)
                .originCountry(CountryCode.JP)
                .isDeleted(false)
                .creationDate(TEST_DATE_TIME);
    }
}
