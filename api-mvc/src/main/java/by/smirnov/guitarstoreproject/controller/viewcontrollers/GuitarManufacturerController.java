package by.smirnov.guitarstoreproject.controller.viewcontrollers;

import by.smirnov.guitarstoreproject.dto.converters.GuitarManufacturerConverter;
import by.smirnov.guitarstoreproject.dto.manufacturer.GuitarManufacturerRequest;
import by.smirnov.guitarstoreproject.model.GuitarManufacturer;
import by.smirnov.guitarstoreproject.service.GuitarManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static by.smirnov.guitarstoreproject.constants.ControllerConstants.*;
import static by.smirnov.guitarstoreproject.constants.GuitarManufacturerControllerConstants.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(MAPPING_MANUFACTURERS)
public class GuitarManufacturerController {

    private final GuitarManufacturerService service;

    private final GuitarManufacturerConverter converter;

    public static final String VIEW_DIRECTORY = MANUFACTURERS;

    @GetMapping()
    public String index(Model model, int pageNumber, int pageSize) {
        model.addAttribute(MANUFACTURERS,
                service.findAll(pageNumber, pageSize).stream()
                        .map(converter::convert)
                        .toList());
        return VIEW_DIRECTORY + MAPPING_INDEX;
    }

    @GetMapping(MAPPING_ID)
    public String show(@PathVariable(ID) long id, Model model) {
        model.addAttribute(MANUFACTURER, converter.convert(service.findById(id)));
        return VIEW_DIRECTORY + MAPPING_SHOW;
    }

    @GetMapping(MAPPING_NEW)
    public String newManufacturer(@ModelAttribute(MANUFACTURER) GuitarManufacturerRequest request) {
        return VIEW_DIRECTORY + MAPPING_NEW;
    }

    @PostMapping()
    public String create(
            @ModelAttribute(MANUFACTURER) @Valid GuitarManufacturerRequest request,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) return VIEW_DIRECTORY + MAPPING_NEW;

        service.create(converter.convert(request));
        return REDIRECT + MAPPING_MANUFACTURERS;
    }

    @GetMapping(MAPPING_EDIT_BY_ID)
    public String edit(Model model, @PathVariable(ID) long id) {
        model.addAttribute(MANUFACTURER, service.findById(id));
        return VIEW_DIRECTORY + MAPPING_EDIT;
    }

    @PatchMapping(MAPPING_ID)
    public String update(
            @ModelAttribute(MANUFACTURER) @Valid GuitarManufacturerRequest request,
            BindingResult bindingResult,
            @PathVariable(ID) long id) {

        if (bindingResult.hasErrors()) return VIEW_DIRECTORY + MAPPING_EDIT;

        service.update(converter.convert(request, id));
        return REDIRECT + MAPPING_MANUFACTURERS;
    }

    @DeleteMapping(MAPPING_ID)
    public String delete(@PathVariable(ID) long id) {
        service.delete(id);
        return REDIRECT + MAPPING_MANUFACTURERS;
    }

}
