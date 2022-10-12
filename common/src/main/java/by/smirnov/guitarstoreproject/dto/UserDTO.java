package by.smirnov.guitarstoreproject.dto;

import by.smirnov.guitarstoreproject.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

import static by.smirnov.guitarstoreproject.model.ValidationConstants.*;
import static by.smirnov.guitarstoreproject.model.ValidationConstants.STANDARD_SIZE_MESSAGE;

@Data
@ToString
@EqualsAndHashCode
public class UserDTO implements ObjectDTO{

    @Null
    private Long id;

    @NotBlank(message = NO_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=STANDARD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String firstName;

    @NotBlank(message = NO_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=STANDARD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String lastName;

    private Role role;

    @NotBlank(message = NO_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=STANDARD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String login;

    @NotBlank(message = NO_BLANK_MESSAGE)
    @Size(min=PASSWORD_MIN_SIZE, max=PASSWORD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String password;

    private Timestamp terminationDate;

    @Size(max=ADDRESS_MAX_SIZE, message = MAX_SIZE_MESSAGE)
    private String address;

    @Size(max=PASSPORT_MAX_SIZE, message = MAX_SIZE_MESSAGE)
    private String passportNumber;
    
    @JsonIgnoreProperties("customer")
    private List<OrderDTO> orders;
}
