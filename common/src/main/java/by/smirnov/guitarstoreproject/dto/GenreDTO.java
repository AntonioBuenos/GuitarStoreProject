package by.smirnov.guitarstoreproject.dto;

import by.smirnov.guitarstoreproject.model.enums.MusicGenre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.util.List;

import static by.smirnov.guitarstoreproject.validation.ValidationConstants.NOT_BLANK_MESSAGE;
import static by.smirnov.guitarstoreproject.validation.ValidationConstants.NULL_MESSAGE;

@Getter
@Setter
@Schema(description = "Musical genre of ordinary guitar usage information")
public class GenreDTO implements ObjectDTO{

    @Schema(description = "Genre identification number")
    @Null(message = NULL_MESSAGE)
    private Long id;

    @Schema(description = "Genre name")
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Enumerated(EnumType.STRING)
    private MusicGenre musicGenre;

    @Schema(description = "List of guitars by this genre")
    @Null(message = NULL_MESSAGE)
    @JsonIgnoreProperties("guitarGenres")
    private List<GuitarDTO> byGenreGuitars;
}
