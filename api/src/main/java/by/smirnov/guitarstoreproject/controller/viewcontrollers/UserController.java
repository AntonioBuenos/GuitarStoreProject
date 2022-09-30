package by.smirnov.guitarstoreproject.controller.viewcontrollers;

import by.smirnov.guitarstoreproject.controller.constants.UserControllerConstants;
import by.smirnov.guitarstoreproject.dto.UserDTO;
import by.smirnov.guitarstoreproject.model.User;
import by.smirnov.guitarstoreproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static by.smirnov.guitarstoreproject.controller.constants.ControllerConstants.*;
import static by.smirnov.guitarstoreproject.controller.constants.GenreControllerConstants.GENRES;
import static by.smirnov.guitarstoreproject.controller.constants.UserControllerConstants.MAPPING_USERS;

@Controller
@RequiredArgsConstructor
@RequestMapping(MAPPING_USERS)
public class UserController {
    private final UserService service;

    private final ModelMapper modelMapper;

    public static final String VIEW_DIRECTORY = GENRES;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute(UserControllerConstants.USERS,
                service.findAll().stream().map(this::convertToDTO).toList());
        return VIEW_DIRECTORY + MAPPING_INDEX;
    }

    @GetMapping(MAPPING_DELETED)
    public String showDeleted(Model model) {
        model.addAttribute(UserControllerConstants.NOT_USERS,
                service.showDeletedUsers().stream().map(this::convertToDTO).toList());
        return VIEW_DIRECTORY + MAPPING_DELETED;
    }

    @GetMapping(MAPPING_ID)
    public String show(@PathVariable(ID) long id, Model model) {
        model.addAttribute(UserControllerConstants.USER, convertToDTO(service.findById(id)));
        return VIEW_DIRECTORY + MAPPING_SHOW;
    }

    @GetMapping(MAPPING_NEW)
    public String newUser(@ModelAttribute(UserControllerConstants.USER) UserDTO userDTO) {
        return VIEW_DIRECTORY + MAPPING_NEW;
    }

    //insert validation
    @PostMapping()
    public String create(@ModelAttribute(UserControllerConstants.USER) UserDTO userDTO) {
        service.create(convertToEntity(userDTO));
        return REDIRECT + MAPPING_USERS;
    }

    @GetMapping(MAPPING_EDIT_BY_ID)
    public String edit(Model model, @PathVariable(ID) long id) {
        model.addAttribute(UserControllerConstants.USER, service.findById(id));
        return VIEW_DIRECTORY + MAPPING_EDIT;
    }

    //insert validation
    @PatchMapping(MAPPING_ID)
    public String update(@ModelAttribute(UserControllerConstants.USER) UserDTO userDTO,
                         @PathVariable(ID) long id) {
        service.update(convertToEntity(userDTO));
        return REDIRECT + MAPPING_USERS;
    }

    @DeleteMapping(MAPPING_ID)
    public String delete(@PathVariable(ID) long id) {
        service.delete(id);
        return REDIRECT + MAPPING_USERS;
    }

    private User convertToEntity(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertToDTO(User user){
        return modelMapper.map(user, UserDTO.class);
    }
}
