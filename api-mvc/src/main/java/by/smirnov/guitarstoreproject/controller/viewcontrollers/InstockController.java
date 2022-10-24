package by.smirnov.guitarstoreproject.controller.viewcontrollers;

import by.smirnov.guitarstoreproject.dto.converters.InstockConverter;
import by.smirnov.guitarstoreproject.dto.instock.InstockCreateRequest;
import by.smirnov.guitarstoreproject.dto.instock.InstockRequest;
import by.smirnov.guitarstoreproject.service.InstockService;
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

import static by.smirnov.guitarstoreproject.constants.ControllerConstants.ID;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_EDIT;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_EDIT_BY_ID;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_ID;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_INDEX;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_NEW;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_SHOW;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.REDIRECT;
import static by.smirnov.guitarstoreproject.constants.InstockControllerConstants.INSTOCK;
import static by.smirnov.guitarstoreproject.constants.InstockControllerConstants.INSTOCKS;
import static by.smirnov.guitarstoreproject.constants.InstockControllerConstants.MAPPING_INSTOCKS;

@Controller
@RequiredArgsConstructor
@RequestMapping(MAPPING_INSTOCKS)
public class InstockController {

    private final InstockService service;

    private final InstockConverter converter;

    public static final String VIEW_DIRECTORY = INSTOCKS;
    public static final String ATTRIBUTE = INSTOCK;

    @GetMapping()
    public String index(Model model, int pageNumber, int pageSize, Sort sort) {
        model.addAttribute(INSTOCKS,
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
    public String newInstock(@ModelAttribute(ATTRIBUTE) InstockCreateRequest request) {
        return VIEW_DIRECTORY + MAPPING_NEW;
    }

    @PostMapping()
    public String create(@ModelAttribute(ATTRIBUTE) @Valid InstockCreateRequest request, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) return VIEW_DIRECTORY + MAPPING_NEW;

        service.create(converter.convert(request));
        return REDIRECT + MAPPING_INSTOCKS;
    }

    @GetMapping(MAPPING_EDIT_BY_ID)
    public String edit(Model model, @PathVariable(ID) long id) {
        model.addAttribute(ATTRIBUTE, service.findById(id));
        return VIEW_DIRECTORY + MAPPING_EDIT;
    }

    @PatchMapping(MAPPING_ID)
    public String update(@ModelAttribute(ATTRIBUTE) @Valid InstockRequest request, BindingResult bindingResult,
                         @PathVariable(ID) long id) {

        if(bindingResult.hasErrors()) return VIEW_DIRECTORY + MAPPING_EDIT;

        service.update(converter.convert(request, id));
        return REDIRECT + MAPPING_INSTOCKS;
    }

    @DeleteMapping(MAPPING_ID)
    public String delete(@PathVariable(ID) long id) {
        service.delete(id);
        return REDIRECT + MAPPING_INSTOCKS;
    }


}
