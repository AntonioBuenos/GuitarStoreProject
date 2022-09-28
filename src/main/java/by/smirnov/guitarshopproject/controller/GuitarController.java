package by.smirnov.guitarshopproject.controller;

import by.smirnov.guitarshopproject.dto.GuitarDTO;
import by.smirnov.guitarshopproject.model.Guitar;
import by.smirnov.guitarshopproject.service.GuitarService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static by.smirnov.guitarshopproject.controller.ControllerConstants.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(MAPPING_GUITARS)
public class GuitarController {
    private final GuitarService service;

    private final ModelMapper modelMapper;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute(GUITARS,
                service.findAll().stream().map(this::convertToDTO).toList());
        return VIEW_GUITARS_INDEX;
    }

    @GetMapping(MAPPING_ID)
    public String show(@PathVariable(ID) long id, Model model) {
        model.addAttribute(GUITAR, convertToDTO(service.findById(id)));
        return VIEW_GUITARS_SHOW;
    }

    @GetMapping(MAPPING_NEW)
    public String newGuitar(@ModelAttribute(GUITAR) GuitarDTO guitarDTO) {
        return "guitars/new";
    }

    //insert validation
    @PostMapping()
    public String create(@ModelAttribute(GUITAR) GuitarDTO guitarDTO) {
        service.create(convertToEntity(guitarDTO));
        return REDIRECT_GUITARS;
    }

    @GetMapping(MAPPING_EDIT)
    public String edit(Model model, @PathVariable(ID) long id) {
        model.addAttribute(GUITAR, service.findById(id));
        return VIEW_GUITARS_EDIT;
    }

    //insert validation
    @PatchMapping(MAPPING_ID)
    public String update(@ModelAttribute(GUITAR) GuitarDTO guitarDTO,
                         @PathVariable(ID) long id) {
        service.update(convertToEntity(guitarDTO));
        return REDIRECT_GUITARS;
    }

    @DeleteMapping(MAPPING_ID)
    public String delete(@PathVariable(ID) long id) {
        service.delete(id);
        return REDIRECT_GUITARS;
    }

    @GetMapping(MAPPING_STATS)
    public String getAveragePrice(Model model) {
        model.addAttribute(AVG, service.showAverageGuitarPrice());
        return VIEW_GUITARS_STATS;
    }

    private Guitar convertToEntity(GuitarDTO guitarDTO){
        return modelMapper.map(guitarDTO, Guitar.class);
    }

    private GuitarDTO convertToDTO(Guitar guitar){
        return modelMapper.map(guitar, GuitarDTO.class);
    }
}
