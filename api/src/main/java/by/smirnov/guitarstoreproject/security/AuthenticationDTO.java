package by.smirnov.guitarstoreproject.security;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AuthenticationDTO {

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 30, message = "имя должно быть от 2 до 100 символов длиной")
    private String login;

    private String password;
}
