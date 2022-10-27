package by.smirnov.guitarstoreproject.dto.user;

import by.smirnov.guitarstoreproject.domain.enums.Role;
import by.smirnov.guitarstoreproject.validation.EnumValid;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

import static by.smirnov.guitarstoreproject.validation.ValidationConstants.NOT_NULL_MESSAGE;

@Getter
@Setter
@Schema(description = "User information")
public class RoleRequest {

    @Schema(description = "User role")
    @EnumValid(enumClass = Role.class)
    @NotNull(message = NOT_NULL_MESSAGE)
    private String role;
}
