package by.smirnov.guitarstoreproject.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static by.smirnov.guitarstoreproject.validation.ValidationConstants.*;
import static by.smirnov.guitarstoreproject.validation.ValidationConstants.STANDARD_SIZE_MESSAGE;

@Getter
@Setter
@Schema(description = "User request for changing credentials")
public class AuthChangeRequest extends AuthRequest{

    @Schema(description = "User authentication login")
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=STANDARD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String newLogin;

    @Schema(description = "User authentication password")
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=PASSWORD_MIN_SIZE, max=PASSWORD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String newPassword;
}
