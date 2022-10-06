package by.smirnov.guitarstoreproject.controller.restcontrollers;

import by.smirnov.guitarstoreproject.controller.constants.UserControllerConstants;
import by.smirnov.guitarstoreproject.dto.GenreDTO;
import by.smirnov.guitarstoreproject.dto.UserDTO;
import by.smirnov.guitarstoreproject.model.User;
import by.smirnov.guitarstoreproject.service.UserService;
import by.smirnov.guitarstoreproject.util.EntityDTOConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static by.smirnov.guitarstoreproject.controller.constants.ControllerConstants.*;
import static by.smirnov.guitarstoreproject.controller.constants.UserControllerConstants.MAPPING_USERS;
import static by.smirnov.guitarstoreproject.controller.constants.UserControllerConstants.USERS;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_REST + MAPPING_USERS)
public class UserRestController {

    private final UserService service;

    private final EntityDTOConverter entityDTOConverter;

    @GetMapping()
    public ResponseEntity<?> index() {
        List<UserDTO> users = service.findAll().stream()
                .map(o -> (UserDTO) entityDTOConverter.convertToDTO(o, UserDTO.class))
                .toList();
        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(Collections.singletonMap(USERS, users), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(MAPPING_ID)
    public ResponseEntity<UserDTO> show(@PathVariable(ID) long id) {
        UserDTO userDTO = (UserDTO) entityDTOConverter.convertToDTO(service.findById(id), UserDTO.class);
        return userDTO != null
                ? new ResponseEntity<>(userDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody UserDTO userDTO) {
        service.create((User) entityDTOConverter.convertToEntity(userDTO, User.class));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping(MAPPING_ID)
    public ResponseEntity<?> update(@PathVariable(name = ID) int id, @RequestBody UserDTO userDTO) {
        User user = (User) entityDTOConverter.convertToEntity(userDTO, User.class);
        final boolean updated = Objects.nonNull(service.update(user));
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(MAPPING_ID)
    public ResponseEntity<?> delete(@PathVariable(ID) long id) {
        User user = service.findById(id);
        if (!user.getIsDeleted()) {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(MAPPING_ID + MAPPING_HARD_DELETE)
    public ResponseEntity<?> hardDelete(@PathVariable(ID) long id) {
        service.hardDelete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(MAPPING_DELETED)
    public ResponseEntity<?> showDeleted() {
        List<UserDTO> deletedUsers = service.showDeletedUsers().stream()
                .map(o -> (UserDTO) entityDTOConverter.convertToDTO(o, UserDTO.class))
                .toList();
        return deletedUsers != null && !deletedUsers.isEmpty()
                ? new ResponseEntity<>(Collections.singletonMap(USERS, deletedUsers), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
