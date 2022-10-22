package by.smirnov.guitarstoreproject.controller.restcontrollers;

import by.smirnov.guitarstoreproject.model.User;
import by.smirnov.guitarstoreproject.model.enums.Role;
import by.smirnov.guitarstoreproject.service.GenreService;
import by.smirnov.guitarstoreproject.service.GuitarManufacturerService;
import by.smirnov.guitarstoreproject.service.GuitarService;
import by.smirnov.guitarstoreproject.service.InstockService;
import by.smirnov.guitarstoreproject.service.OrderService;
import by.smirnov.guitarstoreproject.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static by.smirnov.guitarstoreproject.constants.ControllerConstants.ID;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_ADMIN;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_ID;
import static by.smirnov.guitarstoreproject.constants.GenreControllerConstants.MAPPING_GENRES;
import static by.smirnov.guitarstoreproject.constants.GuitarControllerConstants.MAPPING_GUITARS;
import static by.smirnov.guitarstoreproject.constants.GuitarManufacturerControllerConstants.MAPPING_MANUFACTURERS;
import static by.smirnov.guitarstoreproject.constants.InstockControllerConstants.MAPPING_INSTOCKS;
import static by.smirnov.guitarstoreproject.constants.OrderControllerConstants.MAPPING_ORDERS;
import static by.smirnov.guitarstoreproject.constants.UserControllerConstants.MAPPING_USERS;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_ADMIN)
@Tag(
        name = "Admin functionality Controller",
        description = "Changing user roles and all hard-deletes"
)
public class AdminRestController {

    private final UserService userService;
    private final OrderService orderService;
    private final InstockService instockService;
    private final GuitarService guitarService;
    private final GuitarManufacturerService guitarManufacturerService;
    private final GenreService genreService;

    @Operation(
            summary = "Changing user's role",
            description = "Changing user's role by admin",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
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

    @Operation(
            summary = "User Hard Delete",
            description = "Deletes all user information",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @DeleteMapping(MAPPING_USERS + MAPPING_ID)
    public ResponseEntity<?> userHardDelete(@PathVariable(ID) long id) {
        userService.hardDelete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Order Hard Delete",
            description = "Deletes all order information",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @DeleteMapping(MAPPING_ORDERS + MAPPING_ID)
    public ResponseEntity<?> orderHardDelete(@PathVariable(ID) long id) {
        orderService.hardDelete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Instock Hard Delete",
            description = "Deletes all Instock item information by its ID",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @DeleteMapping(MAPPING_INSTOCKS + MAPPING_ID)
    public ResponseEntity<?> instockHardDelete(@PathVariable(ID) long id) {
        instockService.hardDelete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Guitar Hard Delete",
            description = "Deletes all Guitar information",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @DeleteMapping(MAPPING_GUITARS + MAPPING_ID)
    public ResponseEntity<?> guitarHardDelete(@PathVariable(ID) long id) {
        guitarService.hardDelete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "GuitarManufacturer Hard Delete",
            description = "Deletes all GuitarManufacturer information",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @DeleteMapping(MAPPING_MANUFACTURERS + MAPPING_ID)
    public ResponseEntity<?> manufacturerHardDelete(@PathVariable(ID) long id) {
        guitarManufacturerService.hardDelete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Genre Hard Delete",
            description = "Deletes all Genre information",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @DeleteMapping(MAPPING_GENRES + MAPPING_ID)
    public ResponseEntity<?> genreHardDelete(@PathVariable(ID) long id) {
        genreService.hardDelete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
