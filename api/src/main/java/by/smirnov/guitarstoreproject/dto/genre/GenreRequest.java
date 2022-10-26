package by.smirnov.guitarstoreproject.dto.genre;

import by.smirnov.guitarstoreproject.domain.enums.MusicGenre;
import by.smirnov.guitarstoreproject.validation.EnumValid;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

import static by.smirnov.guitarstoreproject.validation.ValidationConstants.NOT_BLANK_MESSAGE;

@Getter
@Setter
@Schema(description = "Musical genre of ordinary guitar usage information")
public class GenreRequest {

    @Schema(description = "Genre name")
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @EnumValid(enumClass = MusicGenre.class)
    private String musicGenre;
}
