package by.smirnov.guitarstoreproject.dto;

import by.smirnov.guitarstoreproject.model.enums.MusicGenre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GenreDTO implements ObjectDTO{
    private Long id;
    private MusicGenre musicGenre;

    @JsonIgnoreProperties("guitarGenres")
    private List<GuitarDTO> byGenreGuitars;
}
