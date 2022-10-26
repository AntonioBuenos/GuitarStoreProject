package by.smirnov.guitarstoreproject.controller.restcontrollers;

import by.smirnov.guitarstoreproject.domain.GuitarManufacturer;
import by.smirnov.guitarstoreproject.domain.Instock;
import by.smirnov.guitarstoreproject.domain.enums.GoodStatus;
import by.smirnov.guitarstoreproject.dto.converters.GenreConverter;
import by.smirnov.guitarstoreproject.dto.genre.GenreRequest;
import by.smirnov.guitarstoreproject.dto.genre.GenreResponse;
import by.smirnov.guitarstoreproject.domain.Genre;
import by.smirnov.guitarstoreproject.dto.guitar.GuitarResponse;
import by.smirnov.guitarstoreproject.service.GenreService;
import by.smirnov.guitarstoreproject.validation.ValidationErrorConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static by.smirnov.guitarstoreproject.constants.CommonConstants.*;
import static by.smirnov.guitarstoreproject.controller.controllerconstants.GenreControllerConstants.GENRES;
import static by.smirnov.guitarstoreproject.controller.controllerconstants.GenreControllerConstants.MAPPING_GENRES;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.ALREADY_DELETED_MAP;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.BAD_STATUS_MAP;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.DELETED_STATUS;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.NOT_FOUND_MAP;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.PAGE_SIZE;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.PAGE_SORT;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_REST + MAPPING_GENRES)
@Tag(
        name = "Genre Controller",
        description = "All Genre entity methods. CUSTOMERS are authorized for GET methods only."
)
public class GenreRestController {

    private final GenreService service;
    private final GenreConverter converter;

    @Operation(
            summary = "Genres index",
            description = "Returns list of all Genres being not marked deleted.")
    @GetMapping()
    public ResponseEntity<?> index(@ParameterObject
                                   @PageableDefault(sort = PAGE_SORT, size = PAGE_SIZE)
                                   Pageable pageable) {
        List<GenreResponse> genres = service.findAll(pageable).stream()
                .map(converter::convert)
                .toList();
        return new ResponseEntity<>(Collections.singletonMap(GENRES, genres), HttpStatus.OK);
    }

    @Operation(
            summary = "Finding a genre by ID",
            description = "Returns a Genre (not marked deleted) information by its ID")
    @GetMapping(MAPPING_ID)
    public ResponseEntity<?> show(@PathVariable(ID) long id) {

        Genre genre = service.findById(id);
        if (Objects.isNull(genre)) {
            return new ResponseEntity<>(NOT_FOUND_MAP, HttpStatus.NOT_FOUND);
        }

        GenreResponse response = converter.convert(genre);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(
            summary = "New Genre",
            description = "Creates a new Genre.",
            responses = {@ApiResponse(responseCode = "201", description = "Genre created")},
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid GenreRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        Genre created = service.create(converter.convert(request));
        GenreResponse response = converter.convert(created);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(
            summary = "Genre Update",
            description = "Updates Genre by its ID.",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @PutMapping(MAPPING_ID)
    public ResponseEntity<?> update(@PathVariable(name = ID) Long id,
                                    @RequestBody @Valid GenreRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        Genre genre = converter.convert(request, id);
        if (Objects.isNull(genre)) {
            return new ResponseEntity<>(NOT_FOUND_MAP, HttpStatus.NOT_FOUND);
        } else if (Boolean.TRUE.equals(genre.getIsDeleted())) {
            return new ResponseEntity<>(ALREADY_DELETED_MAP, HttpStatus.NOT_MODIFIED);
        }

        Genre changed = service.update(converter.convert(request, id));
        GenreResponse response = converter.convert(changed);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(
            summary = "Genre soft delete",
            description = "This is not a hard delete method. It does not delete genre " +
                    "totally, but changes entity field isDeleted to true that makes it " +
                    "not viewable and keeps it apart from business logic. For hard delete method see " +
                    "Admin Rest Controller, available for ADMIN level users only.",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @DeleteMapping(MAPPING_ID)
    public ResponseEntity<?> delete(@PathVariable(ID) long id) {
        Genre genre = service.findById(id);
        if (Objects.isNull(genre)) {
            return new ResponseEntity<>(NOT_FOUND_MAP, HttpStatus.NOT_FOUND);
        } else if (Boolean.TRUE.equals(genre.getIsDeleted())) {
            return new ResponseEntity<>(ALREADY_DELETED_MAP, HttpStatus.NOT_MODIFIED);
        }

        Genre deleted = service.delete(id);
        return new ResponseEntity<>(
                Collections.singletonMap(DELETED_STATUS, deleted.getIsDeleted()),
                HttpStatus.OK);
    }
}
