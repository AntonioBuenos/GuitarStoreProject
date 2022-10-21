package by.smirnov.guitarstoreproject.dto.genre;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Getter
@Setter
@Schema(description = "Musical genre of ordinary guitar usage information")
public class GenreIdRequest {

    @Schema(description = "Genre identification number")
    @Min(1)
    @Max(Long.MAX_VALUE)
    private Long id;
}
