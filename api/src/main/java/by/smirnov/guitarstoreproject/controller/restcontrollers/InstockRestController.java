package by.smirnov.guitarstoreproject.controller.restcontrollers;

import by.smirnov.guitarstoreproject.controller.exceptionhandle.BadRequestException;
import by.smirnov.guitarstoreproject.controller.exceptionhandle.NoSuchEntityException;
import by.smirnov.guitarstoreproject.controller.exceptionhandle.NotModifiedException;
import by.smirnov.guitarstoreproject.domain.Guitar;
import by.smirnov.guitarstoreproject.domain.Instock;
import by.smirnov.guitarstoreproject.domain.enums.GoodStatus;
import by.smirnov.guitarstoreproject.dto.converters.InstockConverter;
import by.smirnov.guitarstoreproject.dto.instock.InstockCreateRequest;
import by.smirnov.guitarstoreproject.dto.instock.InstockRequest;
import by.smirnov.guitarstoreproject.dto.instock.InstockResponse;
import by.smirnov.guitarstoreproject.service.InstockService;
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

import static by.smirnov.guitarstoreproject.constants.CommonConstants.ID;
import static by.smirnov.guitarstoreproject.constants.CommonConstants.MAPPING_ID;
import static by.smirnov.guitarstoreproject.constants.CommonConstants.MAPPING_REST;
import static by.smirnov.guitarstoreproject.constants.ResponseEntityConstants.BAD_GUITAR_MESSAGE;
import static by.smirnov.guitarstoreproject.constants.ResponseEntityConstants.BAD_STATUS_MESSAGE;
import static by.smirnov.guitarstoreproject.constants.ResponseEntityConstants.GOOD_STATUS;
import static by.smirnov.guitarstoreproject.controller.controllerconstants.CommonControllerConstants.PAGE_SIZE;
import static by.smirnov.guitarstoreproject.controller.controllerconstants.CommonControllerConstants.PAGE_SORT;
import static by.smirnov.guitarstoreproject.controller.controllerconstants.InstockControllerConstants.INSTOCKS;
import static by.smirnov.guitarstoreproject.controller.controllerconstants.InstockControllerConstants.MAPPING_INSTOCKS;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_REST + MAPPING_INSTOCKS)
@Tag(
        name = "Instock Goods Controller",
        description = "All Instock entity methods. CUSTOMERS are authorized for GET methods only."
)
public class InstockRestController {

    private final InstockService service;
    private final InstockConverter converter;

    @Operation(
            summary = "Instocks index",
            description = "Returns list of all instock goods ever received by the Guitar Store, " +
                    "regardless instock statuses."
    )
    @GetMapping()
    public ResponseEntity<?> index(@ParameterObject
                                   @PageableDefault(sort = PAGE_SORT, size = PAGE_SIZE)
                                   Pageable pageable) {
        List<InstockResponse> instokes = service.findAll(pageable)
                .stream()
                .map(converter::convert)
                .toList();
        return new ResponseEntity<>(Collections.singletonMap(INSTOCKS, instokes), HttpStatus.OK);
    }

    @Operation(
            summary = "Finding an instock by ID",
            description = "Returns an Instock item information by its ID regardless its status."
    )
    @GetMapping(MAPPING_ID)
    public ResponseEntity<?> show(@PathVariable(ID) long id) {

        Instock instock = service.findById(id);
        if (Objects.isNull(instock)) {
            throw new NoSuchEntityException();
        }

        InstockResponse response = converter.convert(instock);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(
            summary = "New Instock",
            description = "Creates a new Instock item received by the Guitar Store.",
            responses = {@ApiResponse(responseCode = "201", description = "Instock good created")},
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid InstockCreateRequest request,
                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        Instock instock = converter.convert(request);
        Guitar guitar = instock.getGuitarPosition();
        if (Objects.isNull(guitar) || Boolean.TRUE.equals(guitar.getIsDeleted())) {
            throw new BadRequestException(BAD_GUITAR_MESSAGE);
        }

        Instock created = service.create(instock);
        InstockResponse response = converter.convert(created);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(
            summary = "Instock Update",
            description = "Updates Instock item by his ID. This is update for instock item placement only. " +
                    "Good status is to be changed by Order methods or Instock soft delete method.",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @PutMapping(MAPPING_ID)
    public ResponseEntity<?> update(@PathVariable(name = ID) Long id,
                                    @RequestBody @Valid InstockRequest request,
                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        Instock instock = converter.convert(request, id);
        if (Objects.isNull(instock)) {
            throw new NoSuchEntityException();
        } else if (GoodStatus.OUT_OF_STOCK.equals(instock.getGoodStatus()) ||
                GoodStatus.SOLD.equals(instock.getGoodStatus())) {
            throw new NotModifiedException(BAD_STATUS_MESSAGE);
        }

        Instock changed = service.update(instock);
        InstockResponse response = converter.convert(changed);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(
            summary = "Instock soft delete",
            description = "This is not a hard delete method. It does not delete instock item " +
                    "totally, but changes instock status to OUT_OF_STOCK that keeps it apart " +
                    "from business logic. For hard delete method see Admin Rest Controller, " +
                    "available for ADMIN level users only.",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @DeleteMapping(MAPPING_ID)
    public ResponseEntity<?> delete(@PathVariable(ID) long id) {

        Instock instock = service.findById(id);
        if (Objects.isNull(instock)) {
            throw new NoSuchEntityException();
        } else if (GoodStatus.OUT_OF_STOCK.equals(instock.getGoodStatus()) ||
                GoodStatus.SOLD.equals(instock.getGoodStatus())) {
            throw new NotModifiedException(BAD_STATUS_MESSAGE);
        }

        Instock deleted = service.delete(id);
        return new ResponseEntity<>(
                Collections.singletonMap(GOOD_STATUS, deleted.getGoodStatus()),
                HttpStatus.OK);
    }
}
