package by.smirnov.guitarstoreproject.builder;

import by.smirnov.guitarstoreproject.domain.Guitar;
import com.neovisionaries.i18n.CountryCode;

import java.util.Set;

import static by.smirnov.guitarstoreproject.builder.Brands.aBrand;
import static by.smirnov.guitarstoreproject.builder.Genres.aGenre;
import static by.smirnov.guitarstoreproject.constants.TestConstants.TEST_DATE_TIME;
import static by.smirnov.guitarstoreproject.constants.TestConstants.TEST_ID;
import static by.smirnov.guitarstoreproject.constants.TestConstants.TEST_STRING;
import static by.smirnov.guitarstoreproject.domain.enums.MusicGenre.DJENT;

public class Guitars {

    public static Guitar.GuitarBuilder aGuitar(){
        return Guitar.builder()
                .id(TEST_ID)
                .typeof(TEST_STRING)
                .shape(TEST_STRING)
                .series(TEST_STRING)
                .model(TEST_STRING)
                .stringsQnt(8)
                .neck(TEST_STRING)
                .bodyMaterial(TEST_STRING)
                .price(1099.99)
                .manufacturer(aBrand().build())
                .prodCountry(CountryCode.JP)
                .guitarGenres(Set.of(
                        aGenre().build(),
                        aGenre().id(7L).musicGenre(DJENT).build()))
                .creationDate(TEST_DATE_TIME)
                .isDeleted(false);
    }
}
