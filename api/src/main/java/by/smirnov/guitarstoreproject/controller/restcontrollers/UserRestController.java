package by.smirnov.guitarstoreproject.controller.restcontrollers;

import by.smirnov.guitarstoreproject.dto.converters.UserConverter;
import by.smirnov.guitarstoreproject.dto.user.UserChangeRequest;
import by.smirnov.guitarstoreproject.dto.user.UserResponse;
import by.smirnov.guitarstoreproject.model.User;
import by.smirnov.guitarstoreproject.model.enums.Role;
import by.smirnov.guitarstoreproject.service.UserService;
import by.smirnov.guitarstoreproject.validation.ValidationErrorConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;
import java.util.Objects;

import static by.smirnov.guitarstoreproject.constants.ControllerConstants.ID;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_HARD_DELETE;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_ID;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_REST;
import static by.smirnov.guitarstoreproject.constants.UserControllerConstants.MAPPING_USERS;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_REST + MAPPING_USERS)
@Tag(name = "User Controller", description = "All user entity methods")
public class UserRestController {

    private final UserService service;
    private final UserConverter converter;

    @Operation(
            summary = "User by ID",
            description = "Returns one user information by his ID",
            security = {@SecurityRequirement(name = "JWT Bearer")
            })
    @GetMapping(MAPPING_ID)
    public ResponseEntity<UserResponse> show(@PathVariable(ID) long id, Principal principal) {

        if(isAuthorized(principal.getName(), id)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        UserResponse response = converter.convert(service.findById(id));
        return response != null
                ? new ResponseEntity<>(response, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "User Update",
            description = "Updates user by his ID",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @PatchMapping(MAPPING_ID)
    public ResponseEntity<?> update(@PathVariable(name = ID) Long id,
                                    @RequestBody @Valid UserChangeRequest request,
                                    BindingResult bindingResult,
                                    Principal principal) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        if(isAuthorized(principal.getName(), id)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        User user = converter.convert(request, id);
        final boolean updated = Objects.nonNull(service.update(user));

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(
            summary = "User Soft Delete",
            description = "Sets user field isDeleted to true",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @DeleteMapping(MAPPING_ID)
    public ResponseEntity<?> delete(@PathVariable(ID) long id, Principal principal) {

        if(isAuthorized(principal.getName(), id)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        //review this
        User user = service.findById(id);
        if (!user.getIsDeleted()) {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "User Hard Delete",
            description = "Deletes all user information",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @DeleteMapping(MAPPING_ID + MAPPING_HARD_DELETE)
    public ResponseEntity<?> hardDelete(@PathVariable(ID) long id) {
        service.hardDelete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public boolean isAuthorized(String login, Long id){
        User authenticatedUser = service.findByLogin(login);
        Role authUserRole = authenticatedUser.getRole();
        if(authUserRole == Role.ROLE_ADMIN || authUserRole == Role.ROLE_SALES_CLERC){
            return false;
        }
        Long authenticatedId = authenticatedUser.getId();
        return !Objects.equals(id, authenticatedId);
    }
}
