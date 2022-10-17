package by.smirnov.guitarstoreproject.dto.genre;

import by.smirnov.guitarstoreproject.model.enums.MusicGenre;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

import static by.smirnov.guitarstoreproject.validation.ValidationConstants.NOT_BLANK_MESSAGE;

@Getter
@Setter
@Schema(description = "Musical genre of ordinary guitar usage information")
public class GenreRequest {

    @Schema(description = "Genre name")
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Enumerated(EnumType.STRING)
    private MusicGenre musicGenre;
}
