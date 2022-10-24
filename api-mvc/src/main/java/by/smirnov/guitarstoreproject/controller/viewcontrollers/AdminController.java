package by.smirnov.guitarstoreproject.controller.viewcontrollers;

import by.smirnov.guitarstoreproject.service.GenreService;
import by.smirnov.guitarstoreproject.service.GuitarManufacturerService;
import by.smirnov.guitarstoreproject.service.GuitarService;
import by.smirnov.guitarstoreproject.service.InstockService;
import by.smirnov.guitarstoreproject.service.OrderService;
import by.smirnov.guitarstoreproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static by.smirnov.guitarstoreproject.constants.ControllerConstants.ID;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_ADMIN;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_ID;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.REDIRECT;
import static by.smirnov.guitarstoreproject.constants.GenreControllerConstants.MAPPING_GENRES;
import static by.smirnov.guitarstoreproject.constants.GuitarControllerConstants.MAPPING_GUITARS;
import static by.smirnov.guitarstoreproject.constants.GuitarManufacturerControllerConstants.MAPPING_MANUFACTURERS;
import static by.smirnov.guitarstoreproject.constants.InstockControllerConstants.MAPPING_INSTOCKS;
import static by.smirnov.guitarstoreproject.constants.OrderControllerConstants.MAPPING_ORDERS;
import static by.smirnov.guitarstoreproject.constants.UserControllerConstants.MAPPING_USERS;

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

    @DeleteMapping(MAPPING_USERS + MAPPING_ID)
    public String usersHardDelete(@PathVariable(ID) long id) {
        userService.hardDelete(id);
        return REDIRECT + MAPPING_USERS;
    }

    @DeleteMapping(MAPPING_ORDERS + MAPPING_ID)
    public String ordersHardDelete(@PathVariable(ID) long id) {
        orderService.hardDelete(id);
        return REDIRECT + MAPPING_ORDERS;
    }

    @DeleteMapping(MAPPING_INSTOCKS + MAPPING_ID)
    public String instockHardDelete(@PathVariable(ID) long id) {
        instockService.hardDelete(id);
        return REDIRECT + MAPPING_INSTOCKS;
    }

    @DeleteMapping(MAPPING_MANUFACTURERS + MAPPING_ID)
    public String manufacturerHardDelete(@PathVariable(ID) long id) {
        guitarManufacturerService.hardDelete(id);
        return REDIRECT + MAPPING_GUITARS;
    }

    @DeleteMapping(MAPPING_GUITARS + MAPPING_ID)
    public String guitarHardDelete(@PathVariable(ID) long id) {
        guitarService.hardDelete(id);
        return REDIRECT + MAPPING_MANUFACTURERS;
    }

    @DeleteMapping(MAPPING_GENRES + MAPPING_ID)
    public String genreHardDelete(@PathVariable(ID) long id) {
        genreService.hardDelete(id);
        return REDIRECT + MAPPING_GENRES;
    }
}
