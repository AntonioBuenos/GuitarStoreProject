package by.smirnov.guitarstoreproject.controller.restcontrollers;

import by.smirnov.guitarstoreproject.controller.constants.UserControllerConstants;
import by.smirnov.guitarstoreproject.dto.UserDTO;
import by.smirnov.guitarstoreproject.model.User;
import by.smirnov.guitarstoreproject.service.UserService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_REST + MAPPING_USERS)
public class UserRestController {

    private final UserService service;

    private final ModelMapper modelMapper;

    @GetMapping()
    public ResponseEntity<?> index() {
        List<UserDTO> users = service.findAll().stream().map(this::convertToDTO).toList();
        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(Collections.singletonMap(UserControllerConstants.USERS, users), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(MAPPING_ID)
    public ResponseEntity<UserDTO> show(@PathVariable(ID) long id) {
        UserDTO userDTO = convertToDTO(service.findById(id));
        return userDTO != null
                ? new ResponseEntity<>(userDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody UserDTO userDTO) {
        service.create(convertToEntity(userDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping(MAPPING_ID)
    public ResponseEntity<?> update(@PathVariable(name = ID) int id, @RequestBody UserDTO userDTO) {
        User user = convertToEntity(userDTO);
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

    @GetMapping(MAPPING_DELETED)
    public ResponseEntity<?> showDeleted() {
        List<UserDTO> deletedUsers = service.showDeletedUsers().stream().map(this::convertToDTO).toList();
        return deletedUsers != null && !deletedUsers.isEmpty()
                ? new ResponseEntity<>(Collections.singletonMap(UserControllerConstants.USERS, deletedUsers), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private User convertToEntity(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertToDTO(User user){
        return modelMapper.map(user, UserDTO.class);
    }
}
