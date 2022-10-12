package by.smirnov.guitarstoreproject.security;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import static by.smirnov.guitarstoreproject.model.ValidationConstants.*;
import static by.smirnov.guitarstoreproject.model.ValidationConstants.STANDARD_SIZE_MESSAGE;

@Getter
@Setter
public class AuthenticationDTO {

    @NotEmpty(message = NO_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=STANDARD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String login;

    @NotEmpty(message = NO_BLANK_MESSAGE)
    @Size(min=PASSWORD_MIN_SIZE, max=PASSWORD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String password;
}
