package by.smirnov.guitarstoreproject.controller.viewcontrollers;

import by.smirnov.guitarstoreproject.dto.InstockDTO;
import by.smirnov.guitarstoreproject.model.Instock;
import by.smirnov.guitarstoreproject.service.InstockService;
import by.smirnov.guitarstoreproject.util.EntityDTOConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static by.smirnov.guitarstoreproject.controller.constants.ControllerConstants.*;
import static by.smirnov.guitarstoreproject.controller.constants.InstockControllerConstants.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(MAPPING_INSTOCKS)
public class InstockController {

    private final InstockService service;

    private final EntityDTOConverter entityDTOConverter;

    public static final String VIEW_DIRECTORY = INSTOCKS;
    public static final String ATTRIBUTE = INSTOCK;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute(INSTOCKS,
                service.findAll().stream()
                        .map(o -> (InstockDTO) entityDTOConverter.convertToDTO(o, InstockDTO.class))
                        .toList());
        return VIEW_DIRECTORY + MAPPING_INDEX;
    }

    @GetMapping(MAPPING_ID)
    public String show(@PathVariable(ID) long id, Model model) {
        model.addAttribute(ATTRIBUTE, entityDTOConverter.convertToDTO(service.findById(id), InstockDTO.class));
        return VIEW_DIRECTORY + MAPPING_SHOW;
    }

    @GetMapping(MAPPING_NEW)
    public String newInstock(@ModelAttribute(ATTRIBUTE) InstockDTO instockDTO) {
        return VIEW_DIRECTORY + MAPPING_NEW;
    }

    //insert validation
    @PostMapping()
    public String create(@ModelAttribute(ATTRIBUTE) InstockDTO instockDTO) {
        service.create((Instock) entityDTOConverter.convertToEntity(instockDTO, Instock.class));
        return REDIRECT + MAPPING_INSTOCKS;
    }

    @GetMapping(MAPPING_EDIT_BY_ID)
    public String edit(Model model, @PathVariable(ID) long id) {
        model.addAttribute(ATTRIBUTE, service.findById(id));
        return VIEW_DIRECTORY + MAPPING_EDIT;
    }

    //insert validation
    @PatchMapping(MAPPING_ID)
    public String update(@ModelAttribute(ATTRIBUTE) InstockDTO instockDTO,
                         @PathVariable(ID) long id) {
        service.update((Instock) entityDTOConverter.convertToEntity(instockDTO, Instock.class));
        return REDIRECT + MAPPING_INSTOCKS;
    }

/*    @DeleteMapping(MAPPING_ID)
    public String delete(@PathVariable(ID) long id) {
        service.delete(id);
        return REDIRECT + MAPPING_INSTOCKS;
    }*/
}