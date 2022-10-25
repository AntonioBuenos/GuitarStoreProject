package by.smirnov.guitarstoreproject.dto.user;

import by.smirnov.guitarstoreproject.dto.order.OrderResponse;
import by.smirnov.guitarstoreproject.domain.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@Schema(description = "User information")
public class UserResponse {

    @Schema(description = "User identification number")
    private Long id;

    @Schema(description = "User first name")
    private String firstName;

    @Schema(description = "User last name")
    private String lastName;

    @Schema(description = "User role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Schema(description = "User unique login")
    private String login;

    @Schema(description = "Date/time of user account termination")
    private Timestamp terminationDate;

    @Schema(description = "User residential address")
    private String address;

    @Schema(description = "User passport number")
    private String passportNumber;

    @Schema(description = "User email")
    private String email;

    @Schema(description = "All user's orders ever created")
    @JsonIgnoreProperties("customer")
    private Set<OrderResponse> orders;
}
