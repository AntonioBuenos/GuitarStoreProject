package by.smirnov.guitarstoreproject.controller.restcontrollers;

import by.smirnov.guitarstoreproject.dto.converters.UserConverter;
import by.smirnov.guitarstoreproject.dto.user.UserChangeRequest;
import by.smirnov.guitarstoreproject.dto.user.UserResponse;
import by.smirnov.guitarstoreproject.domain.User;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;
import java.util.Objects;

import static by.smirnov.guitarstoreproject.constants.CommonConstants.ID;
import static by.smirnov.guitarstoreproject.constants.CommonConstants.MAPPING_ID;
import static by.smirnov.guitarstoreproject.constants.CommonConstants.MAPPING_REST;
import static by.smirnov.guitarstoreproject.controller.controllerconstants.UserControllerConstants.MAPPING_USERS;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.ALREADY_DELETED_MAP;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.FORBIDDEN_MAP;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.NOT_FOUND_MAP;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_REST + MAPPING_USERS)
@Tag(name = "User Controller", description = "All user entity methods")
public class UserRestController {

    private final UserService service;
    private final UserConverter converter;
    private final AuthChecker authChecker;

    @Operation(
            summary = "User by ID",
            description = "Returns one user information by his ID",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @GetMapping(MAPPING_ID)
    public ResponseEntity<?> show(@PathVariable(ID) long id, Principal principal) {

        if (authChecker.isAuthorized(principal.getName(), id)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        User user = service.findById(id);
        if (Objects.isNull(user)) return new ResponseEntity<>(NOT_FOUND_MAP, HttpStatus.NOT_FOUND);

        UserResponse response = converter.convert(user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "User Update",
            description = "Updates user by his ID",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @PatchMapping(MAPPING_ID)
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
        } else if (user.getIsDeleted()) {
            return new ResponseEntity<>(ALREADY_DELETED_MAP, HttpStatus.NOT_MODIFIED);
        }

        User updatedUser = converter.convert(request, id);
        service.update(updatedUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "User Soft Delete",
            description = "Sets user field isDeleted to true",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @DeleteMapping(MAPPING_ID)
    public ResponseEntity<?> delete(@PathVariable(ID) long id, Principal principal) {

        User user = service.findById(id);
        if (Objects.isNull(user)) {
            return new ResponseEntity<>(NOT_FOUND_MAP, HttpStatus.NOT_FOUND);
        } else if (!authChecker.isAuthorized(principal.getName(), id)) {
            return new ResponseEntity<>(FORBIDDEN_MAP, HttpStatus.FORBIDDEN);
        } else if (user.getIsDeleted()) {
            return new ResponseEntity<>(ALREADY_DELETED_MAP, HttpStatus.NOT_MODIFIED);
        } else service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
