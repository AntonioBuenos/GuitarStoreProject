package by.smirnov.guitarstoreproject.controller.viewcontrollers;

import by.smirnov.guitarstoreproject.dto.converters.GuitarConverter;
import by.smirnov.guitarstoreproject.dto.guitar.GuitarRequest;
import by.smirnov.guitarstoreproject.service.GuitarService;
import lombok.RequiredArgsConstructor;
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

import static by.smirnov.guitarstoreproject.constants.ControllerConstants.AVG_BY_PRICELIST;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.ID;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_AVG;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_EDIT;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_EDIT_BY_ID;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_ID;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_INDEX;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_NEW;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_SHOW;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.REDIRECT;
import static by.smirnov.guitarstoreproject.constants.GuitarControllerConstants.GUITAR;
import static by.smirnov.guitarstoreproject.constants.GuitarControllerConstants.GUITARS;
import static by.smirnov.guitarstoreproject.constants.GuitarControllerConstants.MAPPING_GUITARS;

@Controller
@RequiredArgsConstructor
@RequestMapping(MAPPING_GUITARS)
public class GuitarController {
    private final GuitarService service;

    private final GuitarConverter converter;

    public static final String VIEW_DIRECTORY = GUITARS;

    @GetMapping()
    public String index(Model model, int pageNumber, int pageSize) {
        model.addAttribute(GUITARS,
                service.findAll(pageNumber, pageSize).stream()
                        .map(converter::convert)
                        .toList());
        return VIEW_DIRECTORY + MAPPING_INDEX;
    }

    @GetMapping(MAPPING_ID)
    public String show(@PathVariable(ID) long id, Model model) {
        model.addAttribute(GUITAR, converter.convert(service.findById(id)));
        return VIEW_DIRECTORY + MAPPING_SHOW;
    }

    @GetMapping(MAPPING_NEW)
    public String newGuitar(@ModelAttribute(GUITAR) GuitarRequest request) {
        return VIEW_DIRECTORY + MAPPING_NEW;
    }

    @PostMapping()
    public String create(
            @ModelAttribute(GUITAR) @Valid GuitarRequest request,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) return VIEW_DIRECTORY + MAPPING_NEW;

        service.create(converter.convert(request));
        return REDIRECT + MAPPING_GUITARS;
    }

    @GetMapping(MAPPING_EDIT_BY_ID)
    public String edit(Model model, @PathVariable(ID) long id) {
        model.addAttribute(GUITAR, service.findById(id));
        return VIEW_DIRECTORY + MAPPING_EDIT;
    }

    @PatchMapping(MAPPING_ID)
    public String update(
            @ModelAttribute(GUITAR) @Valid GuitarRequest request,
            BindingResult bindingResult,
            @PathVariable(ID) long id) {

        if (bindingResult.hasErrors()) return VIEW_DIRECTORY + MAPPING_EDIT;

        service.update(converter.convert(request, id));
        return REDIRECT + MAPPING_GUITARS;
    }

    @DeleteMapping(MAPPING_ID)
    public String delete(@PathVariable(ID) long id) {
        service.delete(id);
        return REDIRECT + MAPPING_GUITARS;
    }

    @GetMapping(MAPPING_AVG)
    public String getAveragePrice(Model model) {
        model.addAttribute(AVG_BY_PRICELIST, service.showAverageGuitarPrices());
        return VIEW_DIRECTORY + MAPPING_AVG;
    }
}
