package by.smirnov.guitarstoreproject.controller.restcontrollers;

import by.smirnov.guitarstoreproject.dto.OrderDTO;
import by.smirnov.guitarstoreproject.model.Order;
import by.smirnov.guitarstoreproject.service.OrderService;
import by.smirnov.guitarstoreproject.util.EntityDTOConverter;
import by.smirnov.guitarstoreproject.util.ValidationErrorConverter;
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

import static by.smirnov.guitarstoreproject.controller.constants.ControllerConstants.*;
import static by.smirnov.guitarstoreproject.controller.constants.OrderControllerConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_REST + MAPPING_ORDERS)
public class OrderRestController {

    private final OrderService service;

    private final EntityDTOConverter entityDTOConverter;

    @GetMapping()
    public ResponseEntity<?> index() {
        List<OrderDTO> orders = service.findAll().stream()
                .map(o -> (OrderDTO) entityDTOConverter.convertToDTO(o, OrderDTO.class))
                .toList();
        return orders != null && !orders.isEmpty()
                ? new ResponseEntity<>(Collections.singletonMap(ORDERS, orders), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(MAPPING_ID)
    public ResponseEntity<OrderDTO> show(@PathVariable(ID) long id) {
        OrderDTO orderDTO = (OrderDTO) entityDTOConverter.convertToDTO(service.findById(id), OrderDTO.class);
        return orderDTO != null
                ? new ResponseEntity<>(orderDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid OrderDTO orderDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        service.create((Order) entityDTOConverter.convertToEntity(orderDTO, Order.class));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

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

    @PatchMapping(MAPPING_ID + MAPPING_SUSPEND)
    public ResponseEntity<?> suspendOrder(@PathVariable(name = ID) Long id) {
        final boolean updated = Objects.nonNull(service.suspendOrder(id));
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PatchMapping(MAPPING_ID + MAPPING_COMPLETE)
    public ResponseEntity<?> completeOrder(@PathVariable(name = ID) Long id) {
        final boolean updated = Objects.nonNull(service.completeOrder(id));
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PatchMapping(MAPPING_ID + MAPPING_RESUME)
    public ResponseEntity<?> resumeOrder(@PathVariable(name = ID) Long id) {
        final boolean updated = Objects.nonNull(service.resumeOrder(id));
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(MAPPING_ID)
    public ResponseEntity<?> delete(@PathVariable(name = ID) Long id) {
        final boolean updated = Objects.nonNull(service.cancelOrder(id));
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(MAPPING_ID + MAPPING_HARD_DELETE)
    public ResponseEntity<?> hardDelete(@PathVariable(ID) long id) {
        service.hardDelete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
