package by.smirnov.guitarstoreproject.controller.restcontrollers;

import by.smirnov.guitarstoreproject.exceptionhandle.BadRequestException;
import by.smirnov.guitarstoreproject.exceptionhandle.NoSuchEntityException;
import by.smirnov.guitarstoreproject.exceptionhandle.NotModifiedException;
import by.smirnov.guitarstoreproject.domain.User;
import by.smirnov.guitarstoreproject.domain.enums.Role;
import by.smirnov.guitarstoreproject.dto.converters.UserConverter;
import by.smirnov.guitarstoreproject.dto.user.RoleRequest;
import by.smirnov.guitarstoreproject.dto.user.UserResponse;
import by.smirnov.guitarstoreproject.service.GenreService;
import by.smirnov.guitarstoreproject.service.GuitarManufacturerService;
import by.smirnov.guitarstoreproject.service.GuitarService;
import by.smirnov.guitarstoreproject.service.InstockService;
import by.smirnov.guitarstoreproject.service.OrderService;
import by.smirnov.guitarstoreproject.service.UserService;
import by.smirnov.guitarstoreproject.validation.ValidationErrorConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;

import static by.smirnov.guitarstoreproject.constants.CommonConstants.ID;
import static by.smirnov.guitarstoreproject.constants.CommonConstants.MAPPING_ID;
import static by.smirnov.guitarstoreproject.constants.ResponseEntityConstants.HARD_DELETED;
import static by.smirnov.guitarstoreproject.controller.controllerconstants.AdminControllerConstants.MAPPING_ADMIN;
import static by.smirnov.guitarstoreproject.controller.controllerconstants.GenreControllerConstants.MAPPING_GENRES;
import static by.smirnov.guitarstoreproject.controller.controllerconstants.GuitarControllerConstants.MAPPING_GUITARS;
import static by.smirnov.guitarstoreproject.controller.controllerconstants.GuitarManufacturerControllerConstants.MAPPING_MANUFACTURERS;
import static by.smirnov.guitarstoreproject.controller.controllerconstants.InstockControllerConstants.MAPPING_INSTOCKS;
import static by.smirnov.guitarstoreproject.controller.controllerconstants.OrderControllerConstants.MAPPING_ORDERS;
import static by.smirnov.guitarstoreproject.controller.controllerconstants.UserControllerConstants.MAPPING_USERS;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_ADMIN)
@Tag(
        name = "Admin Functionality Controller",
        description = "Changing user role and all hard-deletes methods only ADMIN is enabled to."
)
public class AdminRestController {

    private final UserService userService;
    private final OrderService orderService;
    private final InstockService instockService;
    private final GuitarService guitarService;
    private final GuitarManufacturerService guitarManufacturerService;
    private final GenreService genreService;
    private final UserConverter userConverter;

    @Operation(
            summary = "Changing user's role",
            description = "Modifies user's role.",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @PutMapping(MAPPING_ID)
    public ResponseEntity<UserResponse> changeUserRole(
            @PathVariable(name = ID) Long id,
            @RequestBody @Valid RoleRequest request,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new BadRequestException(ValidationErrorConverter.getErrors(bindingResult).toString());
        }

        User user = userService.findById(id);
        if (Objects.isNull(user)) throw new NoSuchEntityException();
        else if (Boolean.TRUE.equals(user.getIsDeleted())) throw new NotModifiedException();

        user.setRole(Role.valueOf(request.getRole()));
        User changed = userService.update(user);
        UserResponse response = userConverter.convert(changed);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "User hard delete",
            description = "Deletes all user information. For soft delete methods see delete " +
                    "method in a correspondent controller.",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @DeleteMapping(MAPPING_USERS + MAPPING_ID)
    public ResponseEntity<Map<String, Long>> userHardDelete(@PathVariable(ID) Long id) {
        if (Objects.isNull(userService.findById(id))) throw new NoSuchEntityException();
        userService.hardDelete(id);
        return new ResponseEntity<>(Map.of(HARD_DELETED, id), HttpStatus.OK);
    }

    @Operation(
            summary = "Order hard delete",
            description = "Deletes all order information. For soft delete methods see delete " +
                    "method in a correspondent controller.",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @DeleteMapping(MAPPING_ORDERS + MAPPING_ID)
    public ResponseEntity<Map<String, Long>> orderHardDelete(@PathVariable(ID) Long id) {
        if (Objects.isNull(orderService.findById(id))) throw new NoSuchEntityException();
        orderService.hardDelete(id);
        return new ResponseEntity<>(Map.of(HARD_DELETED, id), HttpStatus.OK);
    }

    @Operation(
            summary = "Instock hard delete",
            description = "Deletes all Instock item information. For soft delete methods see delete " +
                    "method in a correspondent controller.",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @DeleteMapping(MAPPING_INSTOCKS + MAPPING_ID)
    public ResponseEntity<Map<String, Long>> instockHardDelete(@PathVariable(ID) Long id) {
        if (Objects.isNull(instockService.findById(id))) throw new NoSuchEntityException();
        instockService.hardDelete(id);
        return new ResponseEntity<>(Map.of(HARD_DELETED, id), HttpStatus.OK);
    }

    @Operation(
            summary = "Guitar hard delete",
            description = "Deletes all Guitar information. For soft delete methods see delete " +
                    "method in a correspondent controller.",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @DeleteMapping(MAPPING_GUITARS + MAPPING_ID)
    public ResponseEntity<Map<String, Long>> guitarHardDelete(@PathVariable(ID) Long id) {
        if (Objects.isNull(guitarService.findById(id))) throw new NoSuchEntityException();
        guitarService.hardDelete(id);
        return new ResponseEntity<>(Map.of(HARD_DELETED, id), HttpStatus.OK);
    }

    @Operation(
            summary = "GuitarManufacturer hard delete",
            description = "Deletes all GuitarManufacturer information. For soft delete methods see delete " +
                    "method in a correspondent controller.",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @DeleteMapping(MAPPING_MANUFACTURERS + MAPPING_ID)
    public ResponseEntity<Map<String, Long>> manufacturerHardDelete(@PathVariable(ID) Long id) {
        if (Objects.isNull(guitarManufacturerService.findById(id))) throw new NoSuchEntityException();
        guitarManufacturerService.hardDelete(id);
        return new ResponseEntity<>(Map.of(HARD_DELETED, id), HttpStatus.OK);
    }

    @Operation(
            summary = "Genre hard delete",
            description = "Deletes all Genre information. For soft delete methods see delete " +
                    "method in a correspondent controller.",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @DeleteMapping(MAPPING_GENRES + MAPPING_ID)
    public ResponseEntity<Map<String, Long>> genreHardDelete(@PathVariable(ID) Long id) {
        if (Objects.isNull(genreService.findById(id))) throw new NoSuchEntityException();
        genreService.hardDelete(id);
        return new ResponseEntity<>(Map.of(HARD_DELETED, id), HttpStatus.OK);
    }
}
