package by.smirnov.guitarstoreproject.controller.restcontrollers;

import by.smirnov.guitarstoreproject.domain.Genre;
import by.smirnov.guitarstoreproject.domain.Instock;
import by.smirnov.guitarstoreproject.dto.converters.GuitarManufacturerConverter;
import by.smirnov.guitarstoreproject.dto.genre.GenreResponse;
import by.smirnov.guitarstoreproject.dto.manufacturer.GuitarManufacturerRequest;
import by.smirnov.guitarstoreproject.dto.manufacturer.GuitarManufacturerResponse;
import by.smirnov.guitarstoreproject.domain.GuitarManufacturer;
import by.smirnov.guitarstoreproject.service.GuitarManufacturerService;
import by.smirnov.guitarstoreproject.validation.ValidationErrorConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
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
import static by.smirnov.guitarstoreproject.controller.controllerconstants.GuitarManufacturerControllerConstants.MANUFACTURERS;
import static by.smirnov.guitarstoreproject.controller.controllerconstants.GuitarManufacturerControllerConstants.MAPPING_MANUFACTURERS;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.ALREADY_DELETED_MAP;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.NOT_FOUND_MAP;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.PAGE_SIZE;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.PAGE_SORT;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_REST + MAPPING_MANUFACTURERS)
@Tag(
        name = "Guitar Manufacturer Controller",
        description = "All GuitarManufacturer (Brand) entity methods. " +
                "CUSTOMERS are authorized for GET methods only."
)
public class GuitarManufacturerRestController {

    private final GuitarManufacturerService service;

    private final GuitarManufacturerConverter converter;

    @Operation(
            summary = "GuitarManufacturers index",
            description = "Returns list of all GuitarManufacturers being not marked deleted.")
    @GetMapping()
    public ResponseEntity<?> index(@ParameterObject
                                   @PageableDefault(sort = PAGE_SORT, size = PAGE_SIZE)
                                   Pageable pageable) {
        List<GuitarManufacturerResponse> manufacturers = service.findAll(pageable)
                .stream()
                .map(converter::convert)
                .toList();
        return new ResponseEntity<>(Collections.singletonMap(MANUFACTURERS, manufacturers), HttpStatus.OK);
    }

    @Operation(
            summary = "Finding a guitar manufacturer (not marked deleted) by ID",
            description = "Returns a guitar manufacturer (brand) item information by its ID")
    @GetMapping(MAPPING_ID)
    public ResponseEntity<?> show(@PathVariable(ID) long id) {

        GuitarManufacturer manufacturer = service.findById(id);
        if (Objects.isNull(manufacturer)) {
            return new ResponseEntity<>(NOT_FOUND_MAP, HttpStatus.NOT_FOUND);
        }

        GuitarManufacturerResponse response = converter.convert(manufacturer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(
            summary = "New GuitarManufacturer",
            description = "Creates a new GuitarManufacturer.",
            responses = {@ApiResponse(responseCode = "201", description = "GuitarManufacturer created")},
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid GuitarManufacturerRequest request,
                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        GuitarManufacturer created = service.create(converter.convert(request));
        GuitarManufacturerResponse response = converter.convert(created);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(
            summary = "Guitar manufacturer brand update",
            description = "Updates GuitarManufacturer by his ID.",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @PutMapping(MAPPING_ID)
    public ResponseEntity<?> update(@PathVariable(name = ID) Long id,
                                    @RequestBody @Valid GuitarManufacturerRequest request,
                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        GuitarManufacturer manufacturer = converter.convert(request, id);
        if (Objects.isNull(manufacturer)) {
            return new ResponseEntity<>(NOT_FOUND_MAP, HttpStatus.NOT_FOUND);
        } else if (Boolean.TRUE.equals(manufacturer.getIsDeleted())) {
            return new ResponseEntity<>(ALREADY_DELETED_MAP, HttpStatus.NOT_MODIFIED);
        }

        GuitarManufacturer changed = service.update(converter.convert(request, id));
        GuitarManufacturerResponse response = converter.convert(changed);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(
            summary = "Guitar manufacturer brand soft delete",
            description = "This is not a hard delete method. It does not delete brand " +
                    "totally, but changes entity field isDeleted to true that makes it " +
                    "not viewable and keeps it apart from business logic. For hard delete method see " +
                    "Admin Rest Controller, available for ADMIN level users only.",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @DeleteMapping(MAPPING_ID)
    public ResponseEntity<?> delete(@PathVariable(ID) long id) {
        GuitarManufacturer guitarManufacturer = service.findById(id);
        if (!guitarManufacturer.getIsDeleted()) {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
