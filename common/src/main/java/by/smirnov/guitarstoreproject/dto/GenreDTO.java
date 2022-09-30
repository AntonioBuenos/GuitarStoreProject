package by.smirnov.guitarstoreproject.dto;

import by.smirnov.guitarstoreproject.model.enums.MusicGenre;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenreDTO {
    private Long id;
    private MusicGenre musicGenre;
}
