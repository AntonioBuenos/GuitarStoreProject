package by.smirnov.guitarstoreproject.controller.restcontrollers;

import by.smirnov.guitarstoreproject.dto.converters.UserConverter;
import by.smirnov.guitarstoreproject.dto.user.UserResponse;
import by.smirnov.guitarstoreproject.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

import static by.smirnov.guitarstoreproject.constants.CommonConstants.MAPPING_DELETED;
import static by.smirnov.guitarstoreproject.constants.CommonConstants.MAPPING_REST;
import static by.smirnov.guitarstoreproject.constants.CommonConstants.MAPPING_SECURED;
import static by.smirnov.guitarstoreproject.controller.controllerconstants.UserControllerConstants.MAPPING_USERS;
import static by.smirnov.guitarstoreproject.controller.controllerconstants.UserControllerConstants.USERS;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.PAGE_SIZE;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.PAGE_SORT;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_REST + MAPPING_SECURED + MAPPING_USERS)
@Tag(
        name = "User Index Controller",
        description = "Users and deleted users indices secured methods (available for MANAGER " +
                "& ADMIN role level users."
)
public class UserIndexRestController {

    private final UserService service;
    private final UserConverter converter;

    @Operation(
            summary = "Users index",
            description = "Returns list of all users having field isDeleted set to false.",
            security = {@SecurityRequirement(name = "JWT Bearer")
            })
    @GetMapping()
    public ResponseEntity<?> index(@ParameterObject
                                   @PageableDefault(sort = PAGE_SORT, size = PAGE_SIZE)
                                   Pageable pageable
    ) {
        List<UserResponse> users = service.findAll(pageable)
                .stream()
                .map(converter::convert)
                .toList();
        return new ResponseEntity<>(Collections.singletonMap(USERS, users), HttpStatus.OK);
    }

    @Operation(
            summary = "Deleted users index",
            description = "Returns list of all users having field isDeleted set to true.",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @GetMapping(MAPPING_DELETED)
    public ResponseEntity<?> showDeleted(
            @ParameterObject
            @PageableDefault(sort = PAGE_SORT, size = PAGE_SIZE)
            Pageable pageable
    ) {
        List<UserResponse> deletedUsers = service.showDeletedUsers(pageable)
                .stream()
                .map(converter::convert)
                .toList();
        return new ResponseEntity<>(Collections.singletonMap(USERS, deletedUsers), HttpStatus.OK);
    }
}
