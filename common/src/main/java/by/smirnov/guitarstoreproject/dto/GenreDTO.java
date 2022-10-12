package by.smirnov.guitarstoreproject.dto;

import by.smirnov.guitarstoreproject.model.enums.MusicGenre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.util.List;

import static by.smirnov.guitarstoreproject.model.ValidationConstants.NOT_BLANK_MESSAGE;
import static by.smirnov.guitarstoreproject.model.ValidationConstants.NULL_MESSAGE;

@Getter
@Setter
public class GenreDTO implements ObjectDTO{

    @Null(message = NULL_MESSAGE)
    private Long id;

    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Enumerated(EnumType.STRING)
    private MusicGenre musicGenre;

    @Null(message = NULL_MESSAGE)
    @JsonIgnoreProperties("guitarGenres")
    private List<GuitarDTO> byGenreGuitars;
}
