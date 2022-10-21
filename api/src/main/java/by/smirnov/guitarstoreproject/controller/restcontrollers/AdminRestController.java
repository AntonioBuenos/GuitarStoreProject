package by.smirnov.guitarstoreproject.controller.restcontrollers;

import by.smirnov.guitarstoreproject.dto.converters.UserConverter;
import by.smirnov.guitarstoreproject.model.User;
import by.smirnov.guitarstoreproject.model.enums.Role;
import by.smirnov.guitarstoreproject.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static by.smirnov.guitarstoreproject.constants.ControllerConstants.ID;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_ADMIN;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_ID;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_ADMIN)
@Tag(name = "Admin functionality Controller", description = "Chanding user roles and all hard-deletes")
public class AdminRestController {

    private final UserService userService;
    private final UserConverter converter;

    @Operation(
            summary = "User Update",
            description = "Updates user by his ID",
            security = {@SecurityRequirement(name = "JWT Bearer")
            })
    @PatchMapping(MAPPING_ID)
    public ResponseEntity<?> changeUserRole(@PathVariable(name = ID) Long id, Role role) {

        User user = userService.findById(id);
        user.setRole(role);
        final boolean updated = Objects.nonNull(userService.update(user));

        //review
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
