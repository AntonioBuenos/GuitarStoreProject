package by.smirnov.guitarstoreproject.controller.restcontrollers;

import by.smirnov.guitarstoreproject.domain.Instock;
import by.smirnov.guitarstoreproject.domain.Order;
import by.smirnov.guitarstoreproject.domain.User;
import by.smirnov.guitarstoreproject.domain.enums.GoodStatus;
import by.smirnov.guitarstoreproject.dto.converters.OrderConverter;
import by.smirnov.guitarstoreproject.dto.order.OrderChangeRequest;
import by.smirnov.guitarstoreproject.dto.order.OrderCreateRequest;
import by.smirnov.guitarstoreproject.dto.order.OrderResponse;
import by.smirnov.guitarstoreproject.security.AuthChecker;
import by.smirnov.guitarstoreproject.service.InstockService;
import by.smirnov.guitarstoreproject.service.OrderService;
import by.smirnov.guitarstoreproject.service.UserService;
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
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static by.smirnov.guitarstoreproject.constants.CommonConstants.ID;
import static by.smirnov.guitarstoreproject.constants.CommonConstants.MAPPING_ID;
import static by.smirnov.guitarstoreproject.constants.CommonConstants.MAPPING_REST;
import static by.smirnov.guitarstoreproject.constants.CommonConstants.MAPPING_SECURED;
import static by.smirnov.guitarstoreproject.controller.controllerconstants.OrderControllerConstants.MAPPING_COMPLETE;
import static by.smirnov.guitarstoreproject.controller.controllerconstants.OrderControllerConstants.MAPPING_ORDERS;
import static by.smirnov.guitarstoreproject.controller.controllerconstants.OrderControllerConstants.MAPPING_RESUME;
import static by.smirnov.guitarstoreproject.controller.controllerconstants.OrderControllerConstants.MAPPING_SUSPEND;
import static by.smirnov.guitarstoreproject.controller.controllerconstants.OrderControllerConstants.ORDERS;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.BAD_CUSTOMER_MAP;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.NOT_FOUND_MAP;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.BAD_INSTOCK_MAP;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.PAGE_SIZE;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.PAGE_SORT;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_REST + MAPPING_ORDERS)
@Tag(
        name = "Order Controller",
        description = "All order entity methods, incl. MANAGER/ADMIN functionality of changing order " +
                "status (suspend, complete & resume orders methods) & cancel order method available " +
                "for CUSTOMER role user also."
)
public class OrderRestController {

    private final OrderService service;
    private final OrderConverter converter;
    private final AuthChecker authChecker;
    private final UserService userService;
    private final InstockService instockService;

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(
            summary = "Orders index",
            description = "Returns list of all orders made non-regarding order status. Not available for CUSTOMERS.",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @GetMapping()
    public ResponseEntity<?> index(@ParameterObject
                                   @PageableDefault(sort = PAGE_SORT, size = PAGE_SIZE)
                                   Pageable pageable) {
        List<OrderResponse> orders = service.findAll(pageable)
                .stream()
                .map(converter::convert)
                .toList();
        return new ResponseEntity<>(Collections.singletonMap(ORDERS, orders), HttpStatus.OK);
    }


    @Operation(
            summary = "Finding an order by ID",
            description = "Returns an order information by its ID. A CUSTOMER is enabled to " +
                    "view his/her order info only. MANAGER/ADMIN may view any.",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @GetMapping(MAPPING_ID)
    public ResponseEntity<?> show(@PathVariable(ID) long id) {

        Order order = service.findById(id);
        if (Objects.isNull(order)) {
            return new ResponseEntity<>(NOT_FOUND_MAP, HttpStatus.NOT_FOUND);
        }

        OrderResponse response = converter.convert(order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "New Order",
            description = "Creates a new order, sets ordered Instock good status to RESERVED. This method " +
                    "is designed to create order on behalf of principal only. If MANAGER/ADMIN uses it, " +
                    "an order will also be created for the user who created it. For order creation on behalf " +
                    "of other user, being not principal, use another (secured) create method/",
            responses = {@ApiResponse(responseCode = "201", description = "Order created")},
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid OrderCreateRequest request,
                                    BindingResult bindingResult,
                                    Principal principal) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        Order order = converter.convert(request, principal.getName());
        Instock instock = order.getInstock();
        if (Objects.isNull(instock) || !instock.getGoodStatus().equals(GoodStatus.AVAILABLE)) {
            return new ResponseEntity<>(BAD_INSTOCK_MAP, HttpStatus.BAD_REQUEST);
        }

        Order created = service.create(order);
        OrderResponse response = converter.convert(created);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(
            summary = "New Order",
            description = "Creates a new order, sets ordered Instock good status to RESERVED. " +
                    "This is a MANAGER/ADMIN functionality of order creation on behalf of CUSTOMERS " +
                    "(e.g., for tracing orders gotten by phone and other means). For order creation " +
                    "'for yourself' use another create method (not secured).",
            responses = {@ApiResponse(responseCode = "201", description = "Order created")},
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @PostMapping(MAPPING_SECURED)
    public ResponseEntity<?> create(@RequestBody @Valid OrderCreateRequest request,
                                    BindingResult bindingResult,
                                    Long userId) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        Order order = converter.convert(request, userId);
        User customer = order.getCustomer();
        Instock instock = order.getInstock();
        if (Objects.isNull(instock) || !instock.getGoodStatus().equals(GoodStatus.AVAILABLE)) {
            return new ResponseEntity<>(BAD_INSTOCK_MAP, HttpStatus.BAD_REQUEST);
        }
        else if (Objects.isNull(customer) || Boolean.TRUE.equals(customer.getIsDeleted())) {
            return new ResponseEntity<>(BAD_CUSTOMER_MAP, HttpStatus.BAD_REQUEST);
        }

        Order created = service.create(order);
        OrderResponse response = converter.convert(created);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @Operation(
            summary = "Order Update",
            description = "Updates order by his ID. A CUSTOMER is enabled to modify his/her order only. " +
                    "MANAGER/ADMIN may modify any. This is update for delivery address only. " +
                    "For changing order status use other special methods.",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @PutMapping(MAPPING_ID)
    public ResponseEntity<?> update(@PathVariable(name = ID) Long id,
                                    @RequestBody @Valid OrderChangeRequest request,
                                    BindingResult bindingResult,
                                    Principal principal) {

        Long userId = service.findById(id).getCustomer().getId();
        if (authChecker.isAuthorized(principal.getName(), userId)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        Order order = converter.convert(request, id);
        final boolean updated = Objects.nonNull(service.update(order));
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(
            summary = "Suspend order",
            description = "Sets order status to SUSPENDED. It's aimed to mark order suspended for any reason. " +
                    "Does not cancel ordered instock item reservation. MANAGER/ADMIN functionality only.",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @PutMapping(MAPPING_ID + MAPPING_SUSPEND)
    public ResponseEntity<?> suspendOrder(@PathVariable(name = ID) Long id) {
        final boolean updated = Objects.nonNull(service.suspendOrder(id));
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(
            summary = "Complete order",
            description = "Sets order status to COMPLETED and ordered instock item status to SOLD. " +
                    "MANAGER/ADMIN functionality only.",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @PutMapping(MAPPING_ID + MAPPING_COMPLETE)
    public ResponseEntity<?> completeOrder(@PathVariable(name = ID) Long id) {
        final boolean updated = Objects.nonNull(service.completeOrder(id));
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(
            summary = "Resume order",
            description = "Resets order status to CREATED. MANAGER/ADMIN functionality only.",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @PutMapping(MAPPING_ID + MAPPING_RESUME)
    public ResponseEntity<?> resumeOrder(@PathVariable(name = ID) Long id) {
        final boolean updated = Objects.nonNull(service.resumeOrder(id));
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @Operation(
            summary = "Cancel order",
            description = "Sets order status to CANCELED, cancels reservation of ordered instock good status " +
                    "by resetting it to AVAILABLE. CUSTOMER is enabled to cancel his/her order only. " +
                    "MANAGER/ADMIN may cancel any.",
            security = {@SecurityRequirement(name = "JWT Bearer")})
    @DeleteMapping(MAPPING_ID)
    public ResponseEntity<?> delete(@PathVariable(name = ID) Long id) {
        final boolean updated = Objects.nonNull(service.cancelOrder(id));
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
