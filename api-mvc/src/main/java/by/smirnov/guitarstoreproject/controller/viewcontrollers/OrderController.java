package by.smirnov.guitarstoreproject.controller.viewcontrollers;

import by.smirnov.guitarstoreproject.dto.converters.OrderConverter;
import by.smirnov.guitarstoreproject.dto.order.OrderChangeRequest;
import by.smirnov.guitarstoreproject.dto.order.OrderCreateRequest;
import by.smirnov.guitarstoreproject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

import static by.smirnov.guitarstoreproject.constants.ControllerConstants.ID;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_EDIT;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_EDIT_BY_ID;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_ID;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_INDEX;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_NEW;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_SHOW;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.REDIRECT;
import static by.smirnov.guitarstoreproject.constants.OrderControllerConstants.MAPPING_ORDERS;
import static by.smirnov.guitarstoreproject.constants.OrderControllerConstants.ORDER;
import static by.smirnov.guitarstoreproject.constants.OrderControllerConstants.ORDERS;

@Controller
@RequiredArgsConstructor
@RequestMapping(MAPPING_ORDERS)
public class OrderController {

    private final OrderService service;
    private final OrderConverter converter;
    public static final String VIEW_DIRECTORY = ORDERS;
    public static final String ATTRIBUTE = ORDER;

    @GetMapping()
    public String index(Model model, int pageNumber, int pageSize, Sort sort) {
        model.addAttribute(ORDERS,
                service.findAll(pageNumber, pageSize, sort).stream()
                        .map(converter::convert)
                        .toList());
        return VIEW_DIRECTORY + MAPPING_INDEX;
    }

    @GetMapping(MAPPING_ID)
    public String show(@PathVariable(ID) long id, Model model) {
        model.addAttribute(ATTRIBUTE, converter.convert(service.findById(id)));
        return VIEW_DIRECTORY + MAPPING_SHOW;
    }

    @GetMapping(MAPPING_NEW)
    public String newOrder(@ModelAttribute(ATTRIBUTE) OrderCreateRequest request) {
        return VIEW_DIRECTORY + MAPPING_NEW;
    }

    @PostMapping()
    public String create(
            @ModelAttribute(ATTRIBUTE) @Valid OrderCreateRequest request,
            BindingResult bindingResult,
            Principal principal) {

        if (bindingResult.hasErrors()) return VIEW_DIRECTORY + MAPPING_NEW;

        service.create(converter.convert(request, principal.getName()));
        return REDIRECT + MAPPING_ORDERS;
    }

    @GetMapping(MAPPING_EDIT_BY_ID)
    public String edit(Model model, @PathVariable(ID) long id) {
        model.addAttribute(ATTRIBUTE, service.findById(id));
        return VIEW_DIRECTORY + MAPPING_EDIT;
    }

    @PatchMapping(MAPPING_ID)
    public String update(
            @ModelAttribute(ATTRIBUTE) @Valid OrderChangeRequest request,
            BindingResult bindingResult,
            @PathVariable(ID) long id,
            Principal principal) {

        if (bindingResult.hasErrors()) return VIEW_DIRECTORY + MAPPING_EDIT;

        service.update(converter.convert(request, id));
        return REDIRECT + MAPPING_ORDERS;
    }

    @DeleteMapping(MAPPING_ID)
    public String delete(@PathVariable(ID) long id) {
        service.cancelOrder(id);
        return REDIRECT + MAPPING_ORDERS;
    }


}
