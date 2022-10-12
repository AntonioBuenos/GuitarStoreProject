package by.smirnov.guitarstoreproject.controller.viewcontrollers;

import by.smirnov.guitarstoreproject.controller.constants.GenreControllerConstants;
import by.smirnov.guitarstoreproject.dto.GenreDTO;
import by.smirnov.guitarstoreproject.model.Genre;
import by.smirnov.guitarstoreproject.service.GenreService;
import by.smirnov.guitarstoreproject.util.EntityDTOConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static by.smirnov.guitarstoreproject.controller.constants.ControllerConstants.*;
import static by.smirnov.guitarstoreproject.controller.constants.GenreControllerConstants.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(MAPPING_GENRES)
public class GenreController {

    private final GenreService service;

    private final EntityDTOConverter entityDTOConverter;

    public static final String VIEW_DIRECTORY = GENRES;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute(GenreControllerConstants.GENRES,
                service.findAll().stream()
                        .map(o -> (GenreDTO) entityDTOConverter.convertToDTO(o, GenreDTO.class))
                        .toList());
        return VIEW_DIRECTORY + MAPPING_INDEX;
    }

    @GetMapping(MAPPING_ID)
    public String show(@PathVariable(ID) long id, Model model) {
        model.addAttribute(GENRE, entityDTOConverter.convertToDTO(service.findById(id), GenreDTO.class));
        return VIEW_DIRECTORY + MAPPING_SHOW;
    }

    @GetMapping(MAPPING_NEW)
    public String newGenre(@ModelAttribute(GENRE) GenreDTO genreDTO) {
        return VIEW_DIRECTORY + MAPPING_NEW;
    }

    @PostMapping()
    public String create(@ModelAttribute(GENRE) @Valid GenreDTO genreDTO, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) return VIEW_DIRECTORY + MAPPING_NEW;

        service.create((Genre) entityDTOConverter.convertToEntity(genreDTO, Genre.class));
        return REDIRECT + MAPPING_GENRES;
    }

    @GetMapping(MAPPING_EDIT_BY_ID)
    public String edit(Model model, @PathVariable(ID) long id) {
        model.addAttribute(GENRE, service.findById(id));
        return VIEW_DIRECTORY + MAPPING_EDIT;
    }

    @PatchMapping(MAPPING_ID)
    public String update(@ModelAttribute(GENRE) @Valid GenreDTO genreDTO, BindingResult bindingResult,
                         @PathVariable(ID) long id) {

        if(bindingResult.hasErrors()) return VIEW_DIRECTORY + MAPPING_EDIT;

        service.update((Genre) entityDTOConverter.convertToEntity(genreDTO, Genre.class));
        return REDIRECT + MAPPING_GENRES;
    }

    @DeleteMapping(MAPPING_ID)
    public String delete(@PathVariable(ID) long id) {
        service.delete(id);
        return REDIRECT + MAPPING_GENRES;
    }

    @DeleteMapping(MAPPING_ID + MAPPING_HARD_DELETE)
    public String hardDelete(@PathVariable(ID) long id) {
        service.hardDelete(id);
        return REDIRECT + MAPPING_GENRES;
    }
}
