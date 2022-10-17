package by.smirnov.guitarstoreproject.dto.user;

import by.smirnov.guitarstoreproject.model.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Null;

import static by.smirnov.guitarstoreproject.validation.ValidationConstants.NULL_MESSAGE;

@Getter
@Setter
@Schema(description = "User information")
public class RoleRequest {

    @Schema(description = "User role")
    @Null(message = NULL_MESSAGE)
    @Enumerated(EnumType.STRING)
    private Role role;
}
