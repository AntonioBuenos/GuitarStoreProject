package by.smirnov.guitarstoreproject.controller.restcontrollers;

import by.smirnov.guitarstoreproject.dto.OrderDTO;
import by.smirnov.guitarstoreproject.model.Order;
import by.smirnov.guitarstoreproject.service.OrderService;
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
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static by.smirnov.guitarstoreproject.constants.ControllerConstants.*;
import static by.smirnov.guitarstoreproject.constants.OrderControllerConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_REST + MAPPING_ORDERS)
@Tag(name = "Order Controller", description = "All order entity methods")
public class OrderRestController {

    private final OrderService service;
    private final EntityDTOConverter entityDTOConverter;

    @Operation(
            summary = "Orders index",
            description = "Returns list of all orders made non-regarding order status",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @GetMapping()
    public ResponseEntity<?> index() {
        List<OrderDTO> orders = service.findAll().stream()
                .map(o -> (OrderDTO) entityDTOConverter.convertToDTO(o, OrderDTO.class))
                .toList();
        return orders != null && !orders.isEmpty()
                ? new ResponseEntity<>(Collections.singletonMap(ORDERS, orders), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "Order by ID",
            description = "Returns one order information by its ID",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @GetMapping(MAPPING_ID)
    public ResponseEntity<OrderDTO> show(@PathVariable(ID) long id) {
        OrderDTO orderDTO = (OrderDTO) entityDTOConverter.convertToDTO(service.findById(id), OrderDTO.class);
        return orderDTO != null
                ? new ResponseEntity<>(orderDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "New Order",
            description = "Creates a new order, sets ordered Instock good status to RESERVED",
            responses = {@ApiResponse(responseCode = "201", description = "Order created")})
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid OrderDTO orderDTO, BindingResult bindingResult, Principal principal) {

        String login = principal.getName();

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        service.create((Order) entityDTOConverter.convertToEntity(orderDTO, Order.class), login);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Order Update",
            description = "Updates order by his ID",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @PatchMapping(MAPPING_ID)
    public ResponseEntity<?> update(@PathVariable(name = ID) Long id,
                                    @RequestBody @Valid OrderDTO orderDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        Order order = (Order) entityDTOConverter.convertToEntity(orderDTO, Order.class);
        final boolean updated = Objects.nonNull(service.update(order));
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(
            summary = "Suspend order",
            description = "Sets order status to SUSPENDED",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @PatchMapping(MAPPING_ID + MAPPING_SUSPEND)
    public ResponseEntity<?> suspendOrder(@PathVariable(name = ID) Long id) {
        final boolean updated = Objects.nonNull(service.suspendOrder(id));
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(
            summary = "Complete order",
            description = "Sets order status to COMPLETED and Instock status to SOLD",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @PatchMapping(MAPPING_ID + MAPPING_COMPLETE)
    public ResponseEntity<?> completeOrder(@PathVariable(name = ID) Long id) {
        final boolean updated = Objects.nonNull(service.completeOrder(id));
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(
            summary = "Resume order",
            description = "Resets order status to CREATED",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @PatchMapping(MAPPING_ID + MAPPING_RESUME)
    public ResponseEntity<?> resumeOrder(@PathVariable(name = ID) Long id) {
        final boolean updated = Objects.nonNull(service.resumeOrder(id));
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(
            summary = "Cancel order",
            description = "Sets order status to CANCELED, returns Instock good status to AVAILABLE from RESERVED",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @DeleteMapping(MAPPING_ID)
    public ResponseEntity<?> delete(@PathVariable(name = ID) Long id) {
        final boolean updated = Objects.nonNull(service.cancelOrder(id));
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(
            summary = "Order Hard Delete",
            description = "Deletes all order information",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @DeleteMapping(MAPPING_ID + MAPPING_HARD_DELETE)
    public ResponseEntity<?> hardDelete(@PathVariable(ID) long id) {
        service.hardDelete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
