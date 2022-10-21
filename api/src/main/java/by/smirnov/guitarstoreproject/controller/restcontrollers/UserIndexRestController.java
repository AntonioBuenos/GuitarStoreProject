package by.smirnov.guitarstoreproject.controller.restcontrollers;

import by.smirnov.guitarstoreproject.dto.converters.UserConverter;
import by.smirnov.guitarstoreproject.dto.user.UserResponse;
import by.smirnov.guitarstoreproject.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_DELETED;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_REST;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_SECURED;
import static by.smirnov.guitarstoreproject.constants.UserControllerConstants.MAPPING_USERS;
import static by.smirnov.guitarstoreproject.constants.UserControllerConstants.USERS;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_REST + MAPPING_SECURED + MAPPING_USERS)
@Tag(
        name = "User Index Controller",
        description = "Users and deleted users indices secured methods (available for sales clercs & admins"
)
public class UserIndexRestController {

    private final UserService service;
    private final UserConverter converter;

    @Operation(
            summary = "Users index",
            description = "Returns list of all users having field isDeleted set to false",
            security = {@SecurityRequirement(name = "JWT Bearer")
            })
    @GetMapping()
    public ResponseEntity<?> index(int pageNumber, int pageSize) {
        List<UserResponse> users = service.findAll(pageNumber, pageSize).stream()
                .map(converter::convert)
                .toList();
        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(Collections.singletonMap(USERS, users), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "All deleted users",
            description = "Returns list of all soft deleted users",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @GetMapping(MAPPING_DELETED)
    public ResponseEntity<?> showDeleted(int pageNumber, int pageSize) {
        List<UserResponse> deletedUsers = service.showDeletedUsers(pageNumber, pageSize).stream()
                .map(converter::convert)
                .toList();
        return deletedUsers != null && !deletedUsers.isEmpty()
                ? new ResponseEntity<>(Collections.singletonMap(USERS, deletedUsers), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
