package by.smirnov.guitarstoreproject.controller.restcontrollers;

import by.smirnov.guitarstoreproject.dto.InstockDTO;
import by.smirnov.guitarstoreproject.model.Instock;
import by.smirnov.guitarstoreproject.service.InstockService;
import by.smirnov.guitarstoreproject.util.EntityDTOConverter;
import by.smirnov.guitarstoreproject.validation.ValidationErrorConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static by.smirnov.guitarstoreproject.constants.ControllerConstants.*;
import static by.smirnov.guitarstoreproject.constants.InstockControllerConstants.INSTOCKS;
import static by.smirnov.guitarstoreproject.constants.InstockControllerConstants.MAPPING_INSTOCKS;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_REST + MAPPING_INSTOCKS)
@Tag(name = "Instock Goods Controller", description = "All Instock entity methods")
public class InstockRestController {

    private final InstockService service;
    private final EntityDTOConverter entityDTOConverter;

    @Operation(
            summary = "Instocks index",
            description = "Returns list of all instock goods ever received by the seller company",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @GetMapping()
    public ResponseEntity<?> index() {
        List<InstockDTO> instokes = service.findAll().stream()
                .map(o -> (InstockDTO) entityDTOConverter.convertToDTO(o, InstockDTO.class))
                .toList();
        return instokes != null && !instokes.isEmpty()
                ? new ResponseEntity<>(Collections.singletonMap(INSTOCKS, instokes), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "Instock by ID",
            description = "Returns one Instock item information by its ID",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @GetMapping(MAPPING_ID)
    public ResponseEntity<InstockDTO> show(@PathVariable(ID) long id) {
        InstockDTO instokeDTO = (InstockDTO) entityDTOConverter.convertToDTO(service.findById(id), InstockDTO.class);
        return instokeDTO != null
                ? new ResponseEntity<>(instokeDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "New Instock",
            description = "Creates a new Instock item received by the seller company",
            responses = {@ApiResponse(responseCode = "201", description = "Instock good created")})
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid InstockDTO instokeDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        service.create((Instock) entityDTOConverter.convertToEntity(instokeDTO, Instock.class));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Instock Update",
            description = "Updates Instock item by his ID",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @PatchMapping(MAPPING_ID)
    public ResponseEntity<?> update(@PathVariable(name = ID) int id,
                                    @RequestBody @Valid InstockDTO instokeDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        Instock instoke = (Instock) entityDTOConverter.convertToEntity(instokeDTO, Instock.class);
        final boolean updated = Objects.nonNull(service.update(instoke));
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(
            summary = "Instock Soft Delete",
            description = "Sets instock status to OUT_OF_STOCK",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @DeleteMapping(MAPPING_ID)
    public ResponseEntity<?> delete(@PathVariable(ID) long id) {
        if (Objects.nonNull(service.delete(id))) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(
            summary = "Instock Hard Delete",
            description = "Deletes all Instock item information by its ID",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @DeleteMapping(MAPPING_ID + MAPPING_HARD_DELETE)
    public ResponseEntity<?> hardDelete(@PathVariable(ID) long id) {
        service.hardDelete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
