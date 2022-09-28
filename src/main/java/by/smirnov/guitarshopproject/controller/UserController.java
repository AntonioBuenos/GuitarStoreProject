package by.smirnov.guitarshopproject.controller;

import by.smirnov.guitarshopproject.dto.UserDTO;
import by.smirnov.guitarshopproject.model.User;
import by.smirnov.guitarshopproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static by.smirnov.guitarshopproject.controller.ControllerConstants.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(MAPPING_USERS)
public class UserController {
    private final UserService service;

    private final ModelMapper modelMapper;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute(USERS,
                service.findAll().stream().map(this::convertToDTO).toList());
        return "users/index";
    }

    @GetMapping(MAPPING_DELETED)
    public String showDeleted(Model model) {
        model.addAttribute(NOT_USERS,
                service.showDeletedUsers().stream().map(this::convertToDTO).toList());
        return "users/deleted";
    }

    @GetMapping(MAPPING_ID)
    public String show(@PathVariable(ID) long id, Model model) {
        model.addAttribute(USER, convertToDTO(service.findById(id)));
        return "users/show";
    }

    @GetMapping(MAPPING_NEW)
    public String newUser(@ModelAttribute(USER) UserDTO userDTO) {
        return "users/new";
    }

    //insert validation
    @PostMapping()
    public String create(@ModelAttribute(USER) UserDTO userDTO) {
        service.create(convertToEntity(userDTO));
        return REDIRECT_USERS;
    }

    @GetMapping(MAPPING_EDIT)
    public String edit(Model model, @PathVariable(ID) long id) {
        model.addAttribute(USER, service.findById(id));
        return "users/edit";
    }

    //insert validation
    @PatchMapping(MAPPING_ID)
    public String update(@ModelAttribute(USER) UserDTO userDTO,
                         @PathVariable(ID) long id) {
        service.update(convertToEntity(userDTO));
        return REDIRECT_USERS;
    }

    @DeleteMapping(MAPPING_ID)
    public String delete(@PathVariable(ID) long id) {
        service.delete(id);
        return REDIRECT_USERS;
    }

    private User convertToEntity(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertToDTO(User user){
        return modelMapper.map(user, UserDTO.class);
    }
}
