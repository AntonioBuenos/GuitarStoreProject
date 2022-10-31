package by.smirnov.guitarstoreproject.controller.restcontrollers;

import by.smirnov.guitarstoreproject.controller.exceptionhandle.BadRequestException;
import by.smirnov.guitarstoreproject.controller.exceptionhandle.NoSuchEntityException;
import by.smirnov.guitarstoreproject.controller.exceptionhandle.NotModifiedException;
import by.smirnov.guitarstoreproject.domain.Genre;
import by.smirnov.guitarstoreproject.domain.Guitar;
import by.smirnov.guitarstoreproject.domain.GuitarManufacturer;
import by.smirnov.guitarstoreproject.dto.converters.GuitarConverter;
import by.smirnov.guitarstoreproject.dto.guitar.GuitarRequest;
import by.smirnov.guitarstoreproject.dto.guitar.GuitarResponse;
import by.smirnov.guitarstoreproject.service.GuitarService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static by.smirnov.guitarstoreproject.constants.CommonConstants.ID;
import static by.smirnov.guitarstoreproject.constants.CommonConstants.MAPPING_AVG;
import static by.smirnov.guitarstoreproject.constants.CommonConstants.MAPPING_ID;
import static by.smirnov.guitarstoreproject.constants.CommonConstants.MAPPING_REST;
import static by.smirnov.guitarstoreproject.constants.ResponseEntityConstants.BAD_BRAND_MESSAGE;
import static by.smirnov.guitarstoreproject.constants.ResponseEntityConstants.BAD_GENRE_MESSAGE;
import static by.smirnov.guitarstoreproject.constants.ResponseEntityConstants.DELETED_STATUS;
import static by.smirnov.guitarstoreproject.controller.controllerconstants.CommonControllerConstants.PAGE_SIZE;
import static by.smirnov.guitarstoreproject.controller.controllerconstants.CommonControllerConstants.PAGE_SORT;
import static by.smirnov.guitarstoreproject.controller.controllerconstants.GuitarControllerConstants.GUITARS;
import static by.smirnov.guitarstoreproject.controller.controllerconstants.GuitarControllerConstants.MAPPING_GUITARS;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_REST + MAPPING_GUITARS)
@Tag(
        name = "Guitar Controller",
        description = "All Guitar entity methods. CUSTOMERS are authorized for GET methods only."
)
public class GuitarRestController {
    private final GuitarService service;
    private final GuitarConverter converter;

    @Operation(
            summary = "Guitars index",
            description = "Returns list of all guitar positions in price list " +
                    "being not marked deleted.")
    @GetMapping
    public ResponseEntity<Map<String, List<GuitarResponse>>> index(@ParameterObject
                                   @PageableDefault(sort = PAGE_SORT, size = PAGE_SIZE)
                                   Pageable pageable) {
        List<GuitarResponse> guitars = service.findAll(pageable)
                .stream()
                .map(converter::convert)
                .toList();
        return new ResponseEntity<>(Collections.singletonMap(GUITARS, guitars), HttpStatus.OK);
    }

    @Operation(
            summary = "Finding a guitar by ID",
            description = "Returns a Guitar (not marked deleted) information by its ID")
    @GetMapping(MAPPING_ID)
    public ResponseEntity<GuitarResponse> show(@PathVariable(ID) long id) {

        Guitar guitar = service.findById(id);
        if (Objects.isNull(guitar)) throw new NoSuchEntityException();

        GuitarResponse response = converter.convert(guitar);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(
            summary = "New Guitar",
            description = "Creates a new Guitar in the price list.",
            responses = {@ApiResponse(responseCode = "201", description = "Guitar created")},
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @PostMapping
    public ResponseEntity<GuitarResponse> create(@RequestBody @Valid GuitarRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new BadRequestException(ValidationErrorConverter.getErrors(bindingResult).toString());
        }

        Guitar guitar = converter.convert(request);
        GuitarManufacturer manufacturer = guitar.getManufacturer();
        if (Objects.isNull(manufacturer) || Boolean.TRUE.equals(manufacturer.getIsDeleted())) {
            throw new BadRequestException(BAD_BRAND_MESSAGE);
        }

        Set<Genre> genres = guitar.getGuitarGenres();
        for (Genre genre : genres) {
            if(Objects.isNull(genre) || Boolean.TRUE.equals(genre.getIsDeleted())) {
                throw new BadRequestException(BAD_GENRE_MESSAGE);
            }
        }

        Guitar created = service.create(converter.convert(request));
        GuitarResponse response = converter.convert(created);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(
            summary = "Guitar Update",
            description = "Updates Guitar by its ID.",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @PutMapping(MAPPING_ID)
    public ResponseEntity<GuitarResponse> update(@PathVariable(name = ID) Long id,
                                    @RequestBody @Valid GuitarRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new BadRequestException(ValidationErrorConverter.getErrors(bindingResult).toString());
        }

        Guitar guitar = converter.convert(request, id);
        if (Objects.isNull(guitar)) throw new NoSuchEntityException();

        GuitarManufacturer manufacturer = guitar.getManufacturer();
        if (Objects.isNull(manufacturer) || Boolean.TRUE.equals(manufacturer.getIsDeleted())) {
            throw new BadRequestException(BAD_BRAND_MESSAGE);
        }

        Set<Genre> genres = guitar.getGuitarGenres();
        for (Genre genre : genres) {
            if(Objects.isNull(genre) || Boolean.TRUE.equals(genre.getIsDeleted())) {
                throw new BadRequestException(BAD_GENRE_MESSAGE);
            }
        }

        Guitar changed = service.update(converter.convert(request, id));
        GuitarResponse response = converter.convert(changed);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(
            summary = "Guitar soft delete",
            description = "This is not a hard delete method. It does not delete guitar " +
                    "from price-list totally, but changes entity field isDeleted to true that makes it " +
                    "not viewable and keeps it apart from business logic. For hard delete method see " +
                    "Admin Rest Controller, available for ADMIN level users only.",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @DeleteMapping(MAPPING_ID)
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable(ID) long id) {

        Guitar guitar = service.findById(id);
        if (Objects.isNull(guitar)) throw new NoSuchEntityException();
        else if (Boolean.TRUE.equals(guitar.getIsDeleted())) throw new NotModifiedException();

        Guitar deleted = service.delete(id);
        return new ResponseEntity<>(
                Collections.singletonMap(DELETED_STATUS, deleted.getIsDeleted()),
                HttpStatus.OK);
    }

    @Operation(
            summary = "Guitar average price-list data",
            description = "Returns average prices for " +
                    "(1) all non-deleted guitars in price-list & " +
                    "(2) available in stock guitars"
    )
    @GetMapping(MAPPING_AVG)
    public ResponseEntity<Map<String, String>> getAveragePrices() {
        return new ResponseEntity<>(service.showAverageGuitarPrices(), HttpStatus.OK);
    }
}
