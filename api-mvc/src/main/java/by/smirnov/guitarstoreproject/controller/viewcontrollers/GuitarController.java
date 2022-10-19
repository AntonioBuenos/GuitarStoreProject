package by.smirnov.guitarstoreproject.controller.viewcontrollers;

import by.smirnov.guitarstoreproject.constants.GuitarControllerConstants;
import by.smirnov.guitarstoreproject.model.Guitar;
import by.smirnov.guitarstoreproject.service.GuitarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static by.smirnov.guitarstoreproject.constants.ControllerConstants.*;
import static by.smirnov.guitarstoreproject.constants.GuitarControllerConstants.GUITARS;
import static by.smirnov.guitarstoreproject.constants.GuitarControllerConstants.MAPPING_GUITARS;

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

    @PostMapping()
    public String create(@ModelAttribute(GuitarControllerConstants.GUITAR) @Valid GuitarDTO guitarDTO, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) return VIEW_DIRECTORY + MAPPING_NEW;

        service.create((Guitar) entityDTOConverter.convertToEntity(guitarDTO, Guitar.class));
        return REDIRECT + MAPPING_GUITARS;
    }

    @GetMapping(MAPPING_EDIT_BY_ID)
    public String edit(Model model, @PathVariable(ID) long id) {
        model.addAttribute(GuitarControllerConstants.GUITAR, service.findById(id));
        return VIEW_DIRECTORY + MAPPING_EDIT;
    }

    @PatchMapping(MAPPING_ID)
    public String update(@ModelAttribute(GuitarControllerConstants.GUITAR) @Valid GuitarDTO guitarDTO, BindingResult bindingResult,
                         @PathVariable(ID) long id) {

        if(bindingResult.hasErrors()) return VIEW_DIRECTORY + MAPPING_EDIT;

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

    @GetMapping(MAPPING_AVG_LIST)
    public String getAveragePrice(Model model) {
        model.addAttribute(AVG_BY_PRICELIST, service.showAverageListGuitarPrice());
        return VIEW_DIRECTORY + MAPPING_AVG_LIST;
    }
}
