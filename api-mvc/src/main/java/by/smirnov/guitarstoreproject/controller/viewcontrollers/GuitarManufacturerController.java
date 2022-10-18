package by.smirnov.guitarstoreproject.controller.viewcontrollers;

import by.smirnov.guitarstoreproject.model.GuitarManufacturer;
import by.smirnov.guitarstoreproject.service.GuitarManufacturerService;
import lombok.RequiredArgsConstructor;
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

    private final EntityDTOConverter entityDTOConverter;

    public static final String VIEW_DIRECTORY = MANUFACTURERS;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute(MANUFACTURERS,
                service.findAll().stream()
                        .map(o -> (GuitarManufacturerDTO) entityDTOConverter.convertToDTO(o, GuitarManufacturerDTO.class))
                        .toList());
        return VIEW_DIRECTORY + MAPPING_INDEX;
    }

    @GetMapping(MAPPING_ID)
    public String show(@PathVariable(ID) long id, Model model) {
        model.addAttribute(MANUFACTURER, entityDTOConverter.convertToDTO(service.findById(id), GuitarManufacturerDTO.class));
        return VIEW_DIRECTORY + MAPPING_SHOW;
    }

    @GetMapping(MAPPING_NEW)
    public String newManufacturer(@ModelAttribute(MANUFACTURER) GuitarManufacturerDTO guitarManufacturerDTO) {
        return VIEW_DIRECTORY + MAPPING_NEW;
    }

    @PostMapping()
    public String create(@ModelAttribute(MANUFACTURER) @Valid GuitarManufacturerDTO guitarManufacturerDTO, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) return VIEW_DIRECTORY + MAPPING_NEW;

        service.create((GuitarManufacturer) entityDTOConverter.convertToEntity(guitarManufacturerDTO, GuitarManufacturer.class));
        return REDIRECT + MAPPING_MANUFACTURERS;
    }

    @GetMapping(MAPPING_EDIT_BY_ID)
    public String edit(Model model, @PathVariable(ID) long id) {
        model.addAttribute(MANUFACTURER, service.findById(id));
        return VIEW_DIRECTORY + MAPPING_EDIT;
    }

    @PatchMapping(MAPPING_ID)
    public String update(@ModelAttribute(MANUFACTURER) @Valid GuitarManufacturerDTO guitarManufacturerDTO, BindingResult bindingResult,
                         @PathVariable(ID) long id) {

        if(bindingResult.hasErrors()) return VIEW_DIRECTORY + MAPPING_EDIT;

        service.update((GuitarManufacturer) entityDTOConverter.convertToEntity(guitarManufacturerDTO, GuitarManufacturer.class));
        return REDIRECT + MAPPING_MANUFACTURERS;
    }

    @DeleteMapping(MAPPING_ID)
    public String delete(@PathVariable(ID) long id) {
        service.delete(id);
        return REDIRECT + MAPPING_MANUFACTURERS;
    }

    @DeleteMapping(MAPPING_ID + MAPPING_HARD_DELETE)
    public String hardDelete(@PathVariable(ID) long id) {
        service.hardDelete(id);
        return REDIRECT + MAPPING_MANUFACTURERS;
    }
}
