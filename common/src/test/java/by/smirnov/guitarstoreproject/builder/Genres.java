package by.smirnov.guitarstoreproject.builder;

import by.smirnov.guitarstoreproject.domain.Genre;
import by.smirnov.guitarstoreproject.domain.enums.MusicGenre;

import java.util.ArrayList;

import static by.smirnov.guitarstoreproject.constants.TestConstants.ID;

public class Genres {

    public static Genre.GenreBuilder aGenre(){
        return Genre.builder()
                .id(ID)
                .byGenreGuitars(new ArrayList<>())
                .musicGenre(MusicGenre.METAL)
                .isDeleted(false);
    }
}
