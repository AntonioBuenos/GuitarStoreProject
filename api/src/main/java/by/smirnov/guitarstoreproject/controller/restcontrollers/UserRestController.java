package by.smirnov.guitarstoreproject.controller.restcontrollers;

import by.smirnov.guitarstoreproject.domain.Order;
import by.smirnov.guitarstoreproject.domain.User;
import by.smirnov.guitarstoreproject.dto.converters.UserConverter;
import by.smirnov.guitarstoreproject.dto.order.OrderResponse;
import by.smirnov.guitarstoreproject.dto.user.UserChangeRequest;
import by.smirnov.guitarstoreproject.dto.user.UserResponse;
import by.smirnov.guitarstoreproject.security.AuthChecker;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import static by.smirnov.guitarstoreproject.constants.CommonConstants.ID;
import static by.smirnov.guitarstoreproject.constants.CommonConstants.MAPPING_ID;
import static by.smirnov.guitarstoreproject.constants.CommonConstants.MAPPING_REST;
import static by.smirnov.guitarstoreproject.controller.controllerconstants.UserControllerConstants.MAPPING_USERS;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.ALREADY_DELETED_MAP;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.DELETED_STATUS;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.FORBIDDEN_MAP;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.NOT_FOUND_MAP;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_REST + MAPPING_USERS)
@Tag(name = "User Controller",
        description = "User entity methods allowed for any CUSTOMER role user. " +
        "For listing all non-deleted or all deleted users see User Index Rest Controller. " +
                "These last methods are allowed for MANAGER and ADMIN levels only.")
public class UserRestController {

    private final UserService service;
    private final UserConverter converter;
    private final AuthChecker authChecker;

    @Operation(
            summary = "Finding a user by ID",
            description = "Returns a user information by his/her ID. A CUSTOMER is enabled to " +
                    "view his/her profile only. MANAGER/ADMIN may view any.",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @GetMapping(MAPPING_ID)
    public ResponseEntity<?> show(@PathVariable(ID) long id, Principal principal) {

        if (authChecker.isAuthorized(principal.getName(), id)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        User user = service.findById(id);
        if (Objects.isNull(user)) {
            return new ResponseEntity<>(NOT_FOUND_MAP, HttpStatus.NOT_FOUND);
        }
        UserResponse response = converter.convert(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "User update",
            description = "Updates user by his/her ID. A CUSTOMER is enabled to modify " +
                    "his/her profile only. MANAGER/ADMIN may modify any.",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @PutMapping(MAPPING_ID)
    public ResponseEntity<?> update(@PathVariable(name = ID) Long id,
                                    @RequestBody @Valid UserChangeRequest request,
                                    BindingResult bindingResult,
                                    Principal principal) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        User user = service.findById(id);
        if (Objects.isNull(user)) {
            return new ResponseEntity<>(NOT_FOUND_MAP, HttpStatus.NOT_FOUND);
        } else if (!authChecker.isAuthorized(principal.getName(), id)) {
            return new ResponseEntity<>(FORBIDDEN_MAP, HttpStatus.FORBIDDEN);
        } else if (Boolean.TRUE.equals(user.getIsDeleted())) {
            return new ResponseEntity<>(ALREADY_DELETED_MAP, HttpStatus.NOT_MODIFIED);
        }
        User changed = service.update(converter.convert(request, id));
        UserResponse response = converter.convert(changed);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "User profile delete",
            description = "This is not a hard delete method. It does not delete user " +
                    "profile totally, but changes entity field isDeleted to true that makes it " +
                    "not viewable and keeps it apart from business logic. For hard delete method see " +
                    "Admin Rest Controller, available for ADMIN level users only.",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @DeleteMapping(MAPPING_ID)
    public ResponseEntity<?> delete(@PathVariable(ID) long id, Principal principal) {

        User user = service.findById(id);
        if (Objects.isNull(user)) {
            return new ResponseEntity<>(NOT_FOUND_MAP, HttpStatus.NOT_FOUND);
        } else if (!authChecker.isAuthorized(principal.getName(), id)) {
            return new ResponseEntity<>(FORBIDDEN_MAP, HttpStatus.FORBIDDEN);
        } else if (Boolean.TRUE.equals(user.getIsDeleted())) {
            return new ResponseEntity<>(ALREADY_DELETED_MAP, HttpStatus.NOT_MODIFIED);
        }

        User deleted = service.delete(id);
        return new ResponseEntity<>(
                Collections.singletonMap(DELETED_STATUS, deleted.getIsDeleted()),
                HttpStatus.OK);
    }
}
