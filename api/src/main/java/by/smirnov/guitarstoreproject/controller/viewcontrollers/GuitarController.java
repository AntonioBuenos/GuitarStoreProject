package by.smirnov.guitarstoreproject.controller.viewcontrollers;

import by.smirnov.guitarstoreproject.controller.constants.GuitarControllerConstants;
import by.smirnov.guitarstoreproject.dto.GuitarDTO;
import by.smirnov.guitarstoreproject.model.Guitar;
import by.smirnov.guitarstoreproject.service.GuitarService;
import by.smirnov.guitarstoreproject.util.EntityDTOConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static by.smirnov.guitarstoreproject.controller.constants.ControllerConstants.*;
import static by.smirnov.guitarstoreproject.controller.constants.GuitarControllerConstants.GUITARS;
import static by.smirnov.guitarstoreproject.controller.constants.GuitarControllerConstants.MAPPING_GUITARS;

@Controller
@RequiredArgsConstructor
@RequestMapping(MAPPING_GUITARS)
public class GuitarController {
    private final GuitarService service;

    private final EntityDTOConverter entityDTOConverter;

    public static final String VIEW_DIRECTORY = GUITARS;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute(GUITARS,
                service.findAll().stream()
                        .map(o -> (GuitarDTO) entityDTOConverter.convertToDTO(o, GuitarDTO.class))
                        .toList());
        return VIEW_DIRECTORY + MAPPING_INDEX;
    }

    @GetMapping(MAPPING_ID)
    public String show(@PathVariable(ID) long id, Model model) {
        model.addAttribute(GuitarControllerConstants.GUITAR, entityDTOConverter.convertToDTO(service.findById(id), GuitarDTO.class));
        return VIEW_DIRECTORY + MAPPING_SHOW;
    }

    @GetMapping(MAPPING_NEW)
    public String newGuitar(@ModelAttribute(GuitarControllerConstants.GUITAR) GuitarDTO guitarDTO) {
        return VIEW_DIRECTORY + MAPPING_NEW;
    }

    //insert validation
    @PostMapping()
    public String create(@ModelAttribute(GuitarControllerConstants.GUITAR) GuitarDTO guitarDTO) {
        service.create((Guitar) entityDTOConverter.convertToEntity(guitarDTO, Guitar.class));
        return REDIRECT + MAPPING_GUITARS;
    }

    @GetMapping(MAPPING_EDIT_BY_ID)
    public String edit(Model model, @PathVariable(ID) long id) {
        model.addAttribute(GuitarControllerConstants.GUITAR, service.findById(id));
        return VIEW_DIRECTORY + MAPPING_EDIT;
    }

    //insert validation
    @PatchMapping(MAPPING_ID)
    public String update(@ModelAttribute(GuitarControllerConstants.GUITAR) GuitarDTO guitarDTO,
                         @PathVariable(ID) long id) {
        service.update((Guitar) entityDTOConverter.convertToEntity(guitarDTO, Guitar.class));
        return REDIRECT + MAPPING_GUITARS;
    }

    @DeleteMapping(MAPPING_ID)
    public String delete(@PathVariable(ID) long id) {
        service.delete(id);
        return REDIRECT + MAPPING_GUITARS;
    }

    @DeleteMapping(MAPPING_ID + MAPPING_HARD_DELETE)
    public String hardDelete(@PathVariable(ID) long id) {
        service.hardDelete(id);
        return REDIRECT + MAPPING_GUITARS;
    }

    @GetMapping(MAPPING_STATS)
    public String getAveragePrice(Model model) {
        model.addAttribute(AVG, service.showAverageGuitarPrice());
        return VIEW_DIRECTORY + MAPPING_STATS;
    }
}
