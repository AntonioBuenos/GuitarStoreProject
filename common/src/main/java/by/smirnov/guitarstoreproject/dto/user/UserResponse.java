package by.smirnov.guitarstoreproject.dto.user;

import by.smirnov.guitarstoreproject.dto.OrderDTO;
import by.smirnov.guitarstoreproject.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import static by.smirnov.guitarstoreproject.validation.ValidationConstants.*;
import static by.smirnov.guitarstoreproject.validation.ValidationConstants.NULL_MESSAGE;

@Getter
@Setter
@Schema(description = "User information")
public class UserResponse {

    @Schema(description = "User identification number")
    @Null(message = NULL_MESSAGE)
    private Long id;

    @Schema(description = "User first name")
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=STANDARD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String firstName;

    @Schema(description = "User last name")
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=STANDARD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String lastName;

    @Schema(description = "User role")
    @Null(message = NULL_MESSAGE)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Schema(description = "User unique login")
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=STANDARD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String login;
/*
    @Schema(description = "User password")
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=PASSWORD_MIN_SIZE, max=PASSWORD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String password;*/

    @Schema(description = "Date/time of user account termination")
    @Null(message = NULL_MESSAGE)
    private Timestamp terminationDate;

    @Schema(description = "User residential address")
    @Size(max=ADDRESS_MAX_SIZE, message = MAX_SIZE_MESSAGE)
    private String address;

    @Schema(description = "User passport number")
    @Size(max=PASSPORT_MAX_SIZE, message = MAX_SIZE_MESSAGE)
    private String passportNumber;

    @Schema(description = "All user's orders ever created")
    @Null(message = NULL_MESSAGE)
    @JsonIgnoreProperties("customer")
    private Set<OrderDTO> orders;
}
