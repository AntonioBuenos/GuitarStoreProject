package by.smirnov.guitarstoreproject.builder;

import by.smirnov.guitarstoreproject.domain.Genre;
import by.smirnov.guitarstoreproject.domain.enums.MusicGenre;

import java.util.ArrayList;

import static by.smirnov.guitarstoreproject.constants.TestConstants.TEST_DATE_TIME;
import static by.smirnov.guitarstoreproject.constants.TestConstants.TEST_ID;

public class Genres {

    public static Genre.GenreBuilder aGenre(){
        return Genre.builder()
                .id(TEST_ID)
                .byGenreGuitars(null)
                .musicGenre(MusicGenre.JAZZ)
                .creationDate(TEST_DATE_TIME)
                .isDeleted(false);
    }
}
