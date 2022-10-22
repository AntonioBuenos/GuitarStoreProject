package by.smirnov.guitarstoreproject.controller.restcontrollers;

import by.smirnov.guitarstoreproject.dto.converters.GenreConverter;
import by.smirnov.guitarstoreproject.dto.genre.GenreRequest;
import by.smirnov.guitarstoreproject.dto.genre.GenreResponse;
import by.smirnov.guitarstoreproject.model.Genre;
import by.smirnov.guitarstoreproject.service.GenreService;
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
import static by.smirnov.guitarstoreproject.constants.GenreControllerConstants.GENRES;
import static by.smirnov.guitarstoreproject.constants.GenreControllerConstants.MAPPING_GENRES;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_REST + MAPPING_GENRES)
@Tag(name = "Genre Controller", description = "All Genre entity methods")
public class GenreRestController {

    private final GenreService service;
    private final GenreConverter converter;

    @Operation(
            summary = "Genres index",
            description = "Returns list of all Genres")
    @GetMapping()
    public ResponseEntity<?> index(int pageNumber, int pageSize, Sort sort) {
        List<GenreResponse> genres = service.findAll(pageNumber, pageSize, sort).stream()
                .map(converter::convert)
                .toList();
        return genres != null && !genres.isEmpty()
                ? new ResponseEntity<>(Collections.singletonMap(GENRES, genres), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "Genre by ID",
            description = "Returns one Genre information by its ID")
    @GetMapping(MAPPING_ID)
    public ResponseEntity<GenreResponse> show(@PathVariable(ID) long id) {
        GenreResponse response = converter.convert(service.findById(id));
        return response != null
                ? new ResponseEntity<>(response, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(
            summary = "New Genre",
            description = "Creates a new Genre",
            responses = {@ApiResponse(responseCode = "201", description = "Genre created")},
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid GenreRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        service.create(converter.convert(request));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(
            summary = "Genre Update",
            description = "Updates Genre by his ID",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @PatchMapping(MAPPING_ID)
    public ResponseEntity<?> update(@PathVariable(name = ID) int id,
                                    @RequestBody @Valid GenreRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        Genre genre = converter.convert(request);
        final boolean updated = Objects.nonNull(service.update(genre));
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(
            summary = "Genre Soft Delete",
            description = "Sets Genre field isDeleted to true",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @DeleteMapping(MAPPING_ID)
    public ResponseEntity<?> delete(@PathVariable(ID) long id) {
        Genre genre = service.findById(id);
        if (!genre.getIsDeleted()) {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
