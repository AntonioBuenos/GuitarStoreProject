package by.smirnov.guitarstoreproject.controller.viewcontrollers;

import by.smirnov.guitarstoreproject.constants.UserControllerConstants;
import by.smirnov.guitarstoreproject.model.User;
import by.smirnov.guitarstoreproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static by.smirnov.guitarstoreproject.constants.ControllerConstants.*;
import static by.smirnov.guitarstoreproject.constants.UserControllerConstants.MAPPING_USERS;
import static by.smirnov.guitarstoreproject.constants.UserControllerConstants.USERS;

@Controller
@RequiredArgsConstructor
@RequestMapping(MAPPING_USERS)
public class UserController {
    private final UserService service;
    private final EntityDTOConverter entityDTOConverter;
    public static final String VIEW_DIRECTORY = USERS;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute(UserControllerConstants.USERS,
                service.findAll().stream()
                        .map(o -> (UserDTO) entityDTOConverter.convertToDTO(o, UserDTO.class))
                        .toList());
        return VIEW_DIRECTORY + MAPPING_INDEX;
    }

    @GetMapping(MAPPING_DELETED)
    public String showDeleted(Model model) {
        model.addAttribute(UserControllerConstants.NOT_USERS,
                service.showDeletedUsers().stream()
                        .map(o -> (UserDTO) entityDTOConverter.convertToDTO(o, UserDTO.class))
                        .toList());
        return VIEW_DIRECTORY + MAPPING_DELETED;
    }

    @GetMapping(MAPPING_ID)
    public String show(@PathVariable(ID) long id, Model model) {
        model.addAttribute(UserControllerConstants.USER, entityDTOConverter.convertToDTO(service.findById(id), UserDTO.class));
        return VIEW_DIRECTORY + MAPPING_SHOW;
    }

    @GetMapping(MAPPING_NEW)
    public String newUser(@ModelAttribute(UserControllerConstants.USER) UserDTO userDTO) {
        return VIEW_DIRECTORY + MAPPING_NEW;
    }

    @PostMapping()
    public String create(@ModelAttribute(UserControllerConstants.USER)
                             @Valid UserDTO userDTO, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) return VIEW_DIRECTORY + MAPPING_NEW;

        service.create((User) entityDTOConverter.convertToEntity(userDTO, User.class));
        return REDIRECT + MAPPING_USERS;
    }

    @GetMapping(MAPPING_EDIT_BY_ID)
    public String edit(Model model, @PathVariable(ID) long id) {
        model.addAttribute(UserControllerConstants.USER, service.findById(id));
        return VIEW_DIRECTORY + MAPPING_EDIT;
    }

    @PatchMapping(MAPPING_ID)
    public String update(@ModelAttribute(UserControllerConstants.USER) @Valid UserDTO userDTO,
                         BindingResult bindingResult,
                         @PathVariable(ID) long id) {

        if(bindingResult.hasErrors()) return VIEW_DIRECTORY + MAPPING_EDIT;

        service.update((User) entityDTOConverter.convertToEntity(userDTO, User.class));
        return REDIRECT + MAPPING_USERS;
    }

    @DeleteMapping(MAPPING_ID)
    public String delete(@PathVariable(ID) long id) {
        service.delete(id);
        return REDIRECT + MAPPING_USERS;
    }

    @DeleteMapping(MAPPING_ID + MAPPING_HARD_DELETE)
    public String hardDelete(@PathVariable(ID) long id) {
        service.hardDelete(id);
        return REDIRECT + MAPPING_USERS;
    }
}
