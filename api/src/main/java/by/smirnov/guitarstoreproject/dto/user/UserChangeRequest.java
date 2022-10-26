package by.smirnov.guitarstoreproject.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static by.smirnov.guitarstoreproject.validation.ValidationConstants.*;

@Getter
@Setter
@Schema(description = "User information")
public class UserChangeRequest {
    @Schema(description = "User first name")
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=STANDARD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String firstName;

    @Schema(description = "User last name")
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=STANDARD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String lastName;

    @Schema(description = "User residential address")
    @Size(max=ADDRESS_MAX_SIZE, message = MAX_SIZE_MESSAGE)
    private String address;

    @Schema(description = "User passport number")
    @Size(max=PASSPORT_MAX_SIZE, message = MAX_SIZE_MESSAGE)
    private String passportNumber;

    @Schema(description = "User email")
    @NotNull(message = NOT_NULL_MESSAGE)
    @Email
    private String email;
}
