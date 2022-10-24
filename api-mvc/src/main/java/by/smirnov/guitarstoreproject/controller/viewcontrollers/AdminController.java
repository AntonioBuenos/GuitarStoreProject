package by.smirnov.guitarstoreproject.controller.viewcontrollers;

import by.smirnov.guitarstoreproject.dto.converters.UserConverter;
import by.smirnov.guitarstoreproject.dto.user.RoleRequest;
import by.smirnov.guitarstoreproject.dto.user.UserChangeRequest;
import by.smirnov.guitarstoreproject.dto.user.UserResponse;
import by.smirnov.guitarstoreproject.model.User;
import by.smirnov.guitarstoreproject.model.enums.Role;
import by.smirnov.guitarstoreproject.service.GenreService;
import by.smirnov.guitarstoreproject.service.GuitarManufacturerService;
import by.smirnov.guitarstoreproject.service.GuitarService;
import by.smirnov.guitarstoreproject.service.InstockService;
import by.smirnov.guitarstoreproject.service.OrderService;
import by.smirnov.guitarstoreproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static by.smirnov.guitarstoreproject.constants.AdminControllerConstants.ADMIN;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.ID;
import static by.smirnov.guitarstoreproject.constants.AdminControllerConstants.MAPPING_ADMIN;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_EDIT;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_EDIT_BY_ID;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_ID;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_INDEX;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.REDIRECT;
import static by.smirnov.guitarstoreproject.constants.GenreControllerConstants.MAPPING_GENRES;
import static by.smirnov.guitarstoreproject.constants.GuitarControllerConstants.MAPPING_GUITARS;
import static by.smirnov.guitarstoreproject.constants.GuitarManufacturerControllerConstants.MAPPING_MANUFACTURERS;
import static by.smirnov.guitarstoreproject.constants.InstockControllerConstants.MAPPING_INSTOCKS;
import static by.smirnov.guitarstoreproject.constants.OrderControllerConstants.MAPPING_ORDERS;
import static by.smirnov.guitarstoreproject.constants.UserControllerConstants.MAPPING_USERS;
import static by.smirnov.guitarstoreproject.constants.UserControllerConstants.USER;

@RequiredArgsConstructor
@RestController
@RequestMapping(MAPPING_ADMIN)
public class AdminController {

    private final UserService userService;
    private final OrderService orderService;
    private final InstockService instockService;
    private final GuitarService guitarService;
    private final GuitarManufacturerService guitarManufacturerService;
    private final GenreService genreService;
    private final UserConverter converter;

    public static final String VIEW_DIRECTORY = ADMIN;

    @GetMapping(MAPPING_EDIT_BY_ID)
    public String edit(Model model, @PathVariable(ID) long id) {
        UserResponse response = converter.convert(userService.findById(id));
        model.addAttribute(USER, response);
        return VIEW_DIRECTORY + MAPPING_EDIT;
    }

    @PatchMapping(MAPPING_ID)
    public String changeUserRole(@ModelAttribute(USER) @Valid RoleRequest request,
                                 BindingResult bindingResult,
                                 @PathVariable(ID) long id) {

        if (bindingResult.hasErrors()) return VIEW_DIRECTORY + MAPPING_EDIT;

        User user = userService.findById(id);
        user.setRole(Role.valueOf(request.getRole()));
        userService.update(user);
        return REDIRECT + MAPPING_USERS;
    }

    @GetMapping()
    public String index() {
        return VIEW_DIRECTORY + MAPPING_INDEX;
    }

    @DeleteMapping(MAPPING_USERS + MAPPING_ID)
    public String usersHardDelete(@PathVariable(ID) long id) {
        userService.hardDelete(id);
        return REDIRECT + MAPPING_ADMIN;
    }

    @DeleteMapping(MAPPING_ORDERS + MAPPING_ID)
    public String ordersHardDelete(@PathVariable(ID) long id) {
        orderService.hardDelete(id);
        return REDIRECT + MAPPING_ADMIN;
    }

    @DeleteMapping(MAPPING_INSTOCKS + MAPPING_ID)
    public String instockHardDelete(@PathVariable(ID) long id) {
        instockService.hardDelete(id);
        return REDIRECT + MAPPING_ADMIN;
    }

    @DeleteMapping(MAPPING_MANUFACTURERS + MAPPING_ID)
    public String manufacturerHardDelete(@PathVariable(ID) long id) {
        guitarManufacturerService.hardDelete(id);
        return REDIRECT + MAPPING_ADMIN;
    }

    @DeleteMapping(MAPPING_GUITARS + MAPPING_ID)
    public String guitarHardDelete(@PathVariable(ID) long id) {
        guitarService.hardDelete(id);
        return REDIRECT + MAPPING_ADMIN;
    }

    @DeleteMapping(MAPPING_GENRES + MAPPING_ID)
    public String genreHardDelete(@PathVariable(ID) long id) {
        genreService.hardDelete(id);
        return REDIRECT + MAPPING_ADMIN;
    }
}
