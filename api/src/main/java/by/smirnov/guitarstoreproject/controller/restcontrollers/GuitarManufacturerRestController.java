package by.smirnov.guitarstoreproject.controller.restcontrollers;

import by.smirnov.guitarstoreproject.dto.converters.GuitarManufacturerConverter;
import by.smirnov.guitarstoreproject.dto.manufacturer.GuitarManufacturerRequest;
import by.smirnov.guitarstoreproject.dto.manufacturer.GuitarManufacturerResponse;
import by.smirnov.guitarstoreproject.model.GuitarManufacturer;
import by.smirnov.guitarstoreproject.service.GuitarManufacturerService;
import by.smirnov.guitarstoreproject.validation.ValidationErrorConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
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

import static by.smirnov.guitarstoreproject.constants.ControllerConstants.*;
import static by.smirnov.guitarstoreproject.constants.GuitarManufacturerControllerConstants.MANUFACTURERS;
import static by.smirnov.guitarstoreproject.constants.GuitarManufacturerControllerConstants.MAPPING_MANUFACTURERS;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_REST + MAPPING_MANUFACTURERS)
@Tag(name = "GuitarManufacturer Controller", description = "All GuitarManufacturer entity methods")
public class GuitarManufacturerRestController {

    private final GuitarManufacturerService service;

    private final GuitarManufacturerConverter converter;

    @Operation(
            summary = "GuitarManufacturers index",
            description = "Returns list of all GuitarManufacturers")
    @GetMapping()
    public ResponseEntity<?> index(int pageNumber, int pageSize) {
        List<GuitarManufacturerResponse> manufacturers =  service.findAll(pageNumber, pageSize).stream()
                .map(converter::convert)
                .toList();
        return manufacturers != null &&  !manufacturers.isEmpty()
                ? new ResponseEntity<>(Collections.singletonMap(MANUFACTURERS, manufacturers), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "GuitarManufacturer by ID",
            description = "Returns one GuitarManufacturer item information by its ID")
    @GetMapping(MAPPING_ID)
    public ResponseEntity<GuitarManufacturerResponse> show(@PathVariable(ID) long id) {
        GuitarManufacturerResponse response = converter.convert(service.findById(id));
        return response != null
                ? new ResponseEntity<>(response, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAnyRole('SALES_CLERC', 'ADMIN')")
    @Operation(
            summary = "New GuitarManufacturer",
            description = "Creates a new GuitarManufacturer",
            responses = {@ApiResponse(responseCode = "201", description = "GuitarManufacturer created")},
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid GuitarManufacturerRequest request,
                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        service.create(converter.convert(request));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('SALES_CLERC', 'ADMIN')")
    @Operation(
            summary = "GuitarManufacturer Update",
            description = "Updates GuitarManufacturer by his ID",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @PatchMapping(MAPPING_ID)
    public ResponseEntity<?> update(@PathVariable(name = ID) Long id,
                                    @RequestBody @Valid GuitarManufacturerRequest request,
                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        GuitarManufacturer guitarManufacturer = converter.convert(request, id);
        final boolean updated = Objects.nonNull(service.update(guitarManufacturer));
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PreAuthorize("hasAnyRole('SALES_CLERC', 'ADMIN')")
    @Operation(
            summary = "GuitarManufacturer Soft Delete",
            description = "Sets GuitarManufacturer field isDeleted to true",
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
