package by.smirnov.guitarstoreproject.controller.viewcontrollers;

import by.smirnov.guitarstoreproject.constants.UserControllerConstants;
import by.smirnov.guitarstoreproject.dto.converters.UserConverter;
import by.smirnov.guitarstoreproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_DELETED;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_INDEX;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_SECURED;
import static by.smirnov.guitarstoreproject.constants.UserControllerConstants.MAPPING_USERS;

@Controller
@RequiredArgsConstructor
@RequestMapping(MAPPING_SECURED + MAPPING_USERS)
public class UserIndexController {

    private final UserService service;
    private final UserConverter converter;
    public static final String VIEW_DIRECTORY = "secured";

    @GetMapping()
    public String index(Model model, int pageNumber, int pageSize) {
        model.addAttribute(UserControllerConstants.USERS,
                service.findAll(pageNumber, pageSize).stream()
                        .map(converter::convert)
                        .toList());
        return VIEW_DIRECTORY + MAPPING_INDEX;
    }

    @GetMapping(MAPPING_DELETED)
    public String showDeleted(Model model, int pageNumber, int pageSize) {
        model.addAttribute(UserControllerConstants.NOT_USERS,
                service.showDeletedUsers(pageNumber, pageSize).stream()
                        .map(converter::convert)
                        .toList());
        return VIEW_DIRECTORY + MAPPING_DELETED;
    }
}
