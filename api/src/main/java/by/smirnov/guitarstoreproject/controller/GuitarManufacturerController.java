package by.smirnov.guitarstoreproject.controller;

import by.smirnov.guitarstoreproject.dto.GuitarManufacturerDTO;
import by.smirnov.guitarstoreproject.model.GuitarManufacturer;
import by.smirnov.guitarstoreproject.service.GuitarManufacturerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static by.smirnov.guitarstoreproject.controller.ControllerConstants.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(MAPPING_MANUFACTURERS)
public class GuitarManufacturerController {

    private final GuitarManufacturerService service;

    private final ModelMapper modelMapper;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute(MANUFACTURERS,
                service.findAll().stream().map(this::convertToDTO).toList());
        return VIEW_MANUFACTURERS_INDEX;
    }

    @GetMapping(MAPPING_ID)
    public String show(@PathVariable(ID) long id, Model model) {
        model.addAttribute(MANUFACTURER, convertToDTO(service.findById(id)));
        return VIEW_MANUFACTURERS_SHOW;
    }

    @GetMapping(MAPPING_NEW)
    public String newManufacturer(@ModelAttribute(MANUFACTURER) GuitarManufacturerDTO guitarManufacturerDTO) {
        return VIEW_MANUFACTURERS_NEW;
    }

    @PostMapping()
    public String create(@ModelAttribute(MANUFACTURER) GuitarManufacturerDTO guitarManufacturerDTO) {
        service.create(convertToEntity(guitarManufacturerDTO));
        return REDIRECT_MANUFACTURERS;
    }

    @GetMapping(MAPPING_EDIT)
    public String edit(Model model, @PathVariable(ID) long id) {
        model.addAttribute(MANUFACTURER, service.findById(id));
        return VIEW_MANUFACTURERS_EDIT;
    }

    @PatchMapping(MAPPING_ID)
    public String update(@ModelAttribute(MANUFACTURER) GuitarManufacturerDTO guitarManufacturerDTO,
                         @PathVariable(ID) long id) {
        service.update(convertToEntity(guitarManufacturerDTO));
        return REDIRECT_MANUFACTURERS;
    }

    @DeleteMapping(MAPPING_ID)
    public String delete(@PathVariable(ID) long id) {
        service.delete(id);
        return REDIRECT_MANUFACTURERS;
    }

    private GuitarManufacturer convertToEntity(GuitarManufacturerDTO guitarManufacturerDTO){
        return modelMapper.map(guitarManufacturerDTO, GuitarManufacturer.class);
    }

    private GuitarManufacturerDTO convertToDTO(GuitarManufacturer guitarManufacturer){
        return modelMapper.map(guitarManufacturer, GuitarManufacturerDTO.class);
    }
}
