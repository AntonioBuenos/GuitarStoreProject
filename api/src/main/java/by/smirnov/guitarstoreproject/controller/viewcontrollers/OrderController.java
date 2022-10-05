package by.smirnov.guitarstoreproject.controller.viewcontrollers;

import by.smirnov.guitarstoreproject.dto.OrderDTO;
import by.smirnov.guitarstoreproject.model.Order;
import by.smirnov.guitarstoreproject.service.OrderService;
import by.smirnov.guitarstoreproject.util.EntityDTOConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static by.smirnov.guitarstoreproject.controller.constants.ControllerConstants.*;
import static by.smirnov.guitarstoreproject.controller.constants.OrderControllerConstants.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(MAPPING_ORDERS)
public class OrderController {

    private final OrderService service;
    private final EntityDTOConverter entityDTOConverter;
    public static final String VIEW_DIRECTORY = ORDERS;
    public static final String ATTRIBUTE = ORDER;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute(ORDERS,
                service.findAll().stream()
                        .map(o -> (OrderDTO) entityDTOConverter.convertToDTO(o, OrderDTO.class))
                        .toList());
        return VIEW_DIRECTORY + MAPPING_INDEX;
    }

    @GetMapping(MAPPING_ID)
    public String show(@PathVariable(ID) long id, Model model) {
        model.addAttribute(ATTRIBUTE, entityDTOConverter.convertToDTO(service.findById(id), OrderDTO.class));
        return VIEW_DIRECTORY + MAPPING_SHOW;
    }

    @GetMapping(MAPPING_NEW)
    public String newOrder(@ModelAttribute(ATTRIBUTE) OrderDTO orderDTO) {
        return VIEW_DIRECTORY + MAPPING_NEW;
    }

    //insert validation
    @PostMapping()
    public String create(@ModelAttribute(ATTRIBUTE) OrderDTO orderDTO) {
        service.create((Order) entityDTOConverter.convertToEntity(orderDTO, Order.class));
        return REDIRECT + MAPPING_ORDERS;
    }

    @GetMapping(MAPPING_EDIT_BY_ID)
    public String edit(Model model, @PathVariable(ID) long id) {
        model.addAttribute(ATTRIBUTE, service.findById(id));
        return VIEW_DIRECTORY + MAPPING_EDIT;
    }

    //insert validation
    @PatchMapping(MAPPING_ID)
    public String update(@ModelAttribute(ATTRIBUTE) OrderDTO orderDTO,
                         @PathVariable(ID) long id) {
        service.update((Order) entityDTOConverter.convertToEntity(orderDTO, Order.class));
        return REDIRECT + MAPPING_ORDERS;
    }

/*    @DeleteMapping(MAPPING_ID)
    public String delete(@PathVariable(ID) long id) {
        service.delete(id);
        return REDIRECT + MAPPING_ORDERS;
    }*/
}