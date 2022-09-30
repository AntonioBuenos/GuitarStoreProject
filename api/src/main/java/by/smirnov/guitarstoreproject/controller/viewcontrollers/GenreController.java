package by.smirnov.guitarstoreproject.controller.viewcontrollers;

import by.smirnov.guitarstoreproject.dto.GenreDTO;
import by.smirnov.guitarstoreproject.model.Genre;
import by.smirnov.guitarstoreproject.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static by.smirnov.guitarstoreproject.controller.ControllerConstants.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(MAPPING_GENRES)
public class GenreController {

    private final GenreService service;

    private final ModelMapper modelMapper;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute(GENRES,
                service.findAll().stream().map(this::convertToDTO).toList());
        return VIEW_GENRES_INDEX;
    }

    @GetMapping(MAPPING_ID)
    public String show(@PathVariable(ID) long id, Model model) {
        model.addAttribute(GENRE, convertToDTO(service.findById(id)));
        return VIEW_GENRES_SHOW;
    }

    @GetMapping(MAPPING_NEW)
    public String newGenre(@ModelAttribute(GENRE) GenreDTO genreDTO) {
        return VIEW_GENRES_NEW;
    }

    //insert validation
    @PostMapping()
    public String create(@ModelAttribute(GENRE) GenreDTO genreDTO) {
        service.create(convertToEntity(genreDTO));
        return REDIRECT_GENRES;
    }

    @GetMapping(MAPPING_EDIT)
    public String edit(Model model, @PathVariable(ID) long id) {
        model.addAttribute(GENRE, service.findById(id));
        return VIEW_GENRES_EDIT;
    }

    //insert validation
    @PatchMapping(MAPPING_ID)
    public String update(@ModelAttribute(GENRE) GenreDTO genreDTO,
                         @PathVariable(ID) long id) {
        service.update(convertToEntity(genreDTO));
        return REDIRECT_GENRES;
    }

    @DeleteMapping(MAPPING_ID)
    public String delete(@PathVariable(ID) long id) {
        service.delete(id);
        return REDIRECT_GENRES;
    }

    private Genre convertToEntity(GenreDTO genreDTO){
        return modelMapper.map(genreDTO, Genre.class);
    }

    private GenreDTO convertToDTO(Genre genre){
        return modelMapper.map(genre, GenreDTO.class);
    }
}
