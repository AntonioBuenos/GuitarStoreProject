package by.smirnov.guitarstoreproject.dto.genre;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Musical genre of ordinary guitar usage information")
public class GenreIdRequest {

    @Schema(description = "Genre identification number")
    private Long id;
}
