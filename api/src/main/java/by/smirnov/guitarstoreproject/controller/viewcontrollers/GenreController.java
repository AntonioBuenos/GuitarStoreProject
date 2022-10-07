package by.smirnov.guitarstoreproject.controller.viewcontrollers;

import by.smirnov.guitarstoreproject.controller.constants.GenreControllerConstants;
import by.smirnov.guitarstoreproject.dto.GenreDTO;
import by.smirnov.guitarstoreproject.model.Genre;
import by.smirnov.guitarstoreproject.service.GenreService;
import by.smirnov.guitarstoreproject.util.EntityDTOConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
}
