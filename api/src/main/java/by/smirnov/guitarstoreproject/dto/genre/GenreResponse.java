package by.smirnov.guitarstoreproject.dto.genre;

import by.smirnov.guitarstoreproject.dto.guitar.GuitarResponse;
import by.smirnov.guitarstoreproject.model.enums.MusicGenre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Getter
@Setter
@Schema(description = "Musical genre of ordinary guitar usage information")
public class GenreResponse {

    @Schema(description = "Genre identification number")
    private Long id;

    @Schema(description = "Genre name")
    @Enumerated(EnumType.STRING)
    private MusicGenre musicGenre;

    @Schema(description = "List of guitars by this genre")
    @JsonIgnoreProperties("guitarGenres")
    private List<GuitarResponse> byGenreGuitars;
}
