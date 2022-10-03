package by.smirnov.guitarstoreproject.controller.viewcontrollers;

import by.smirnov.guitarstoreproject.controller.constants.GuitarManufacturerControllerConstants;
import by.smirnov.guitarstoreproject.dto.GenreDTO;
import by.smirnov.guitarstoreproject.dto.GuitarDTO;
import by.smirnov.guitarstoreproject.dto.GuitarManufacturerDTO;
import by.smirnov.guitarstoreproject.model.GuitarManufacturer;
import by.smirnov.guitarstoreproject.service.GuitarManufacturerService;
import by.smirnov.guitarstoreproject.util.EntityDTOConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static by.smirnov.guitarstoreproject.controller.constants.ControllerConstants.*;
import static by.smirnov.guitarstoreproject.controller.constants.GuitarControllerConstants.GUITARS;
import static by.smirnov.guitarstoreproject.controller.constants.GuitarManufacturerControllerConstants.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(GuitarManufacturerControllerConstants.MAPPING_MANUFACTURERS)
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
    public String create(@ModelAttribute(MANUFACTURER) GuitarManufacturerDTO guitarManufacturerDTO) {
        service.create((GuitarManufacturer) entityDTOConverter.convertToEntity(guitarManufacturerDTO, GuitarManufacturer.class));
        return REDIRECT + MAPPING_MANUFACTURERS;
    }

    @GetMapping(MAPPING_EDIT_BY_ID)
    public String edit(Model model, @PathVariable(ID) long id) {
        model.addAttribute(MANUFACTURER, service.findById(id));
        return VIEW_DIRECTORY + MAPPING_EDIT;
    }

    @PatchMapping(MAPPING_ID)
    public String update(@ModelAttribute(MANUFACTURER) GuitarManufacturerDTO guitarManufacturerDTO,
                         @PathVariable(ID) long id) {
        service.update((GuitarManufacturer) entityDTOConverter.convertToEntity(guitarManufacturerDTO, GuitarManufacturer.class));
        return REDIRECT + MAPPING_MANUFACTURERS;
    }

    @DeleteMapping(MAPPING_ID)
    public String delete(@PathVariable(ID) long id) {
        service.delete(id);
        return REDIRECT + MAPPING_MANUFACTURERS;
    }
}
