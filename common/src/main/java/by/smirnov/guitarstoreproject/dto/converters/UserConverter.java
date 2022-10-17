package by.smirnov.guitarstoreproject.dto.converters;

import by.smirnov.guitarstoreproject.dto.user.UserChangeRequest;
import by.smirnov.guitarstoreproject.dto.user.UserCreateRequest;
import by.smirnov.guitarstoreproject.dto.user.UserResponse;
import by.smirnov.guitarstoreproject.model.User;
import by.smirnov.guitarstoreproject.model.enums.Role;
import by.smirnov.guitarstoreproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class UserConverter {

    private final ModelMapper modelMapper;
    private final UserService service;

    public User convert(UserCreateRequest request){
        User object = modelMapper.map(request, User.class);
        object.setRole(Role.ROLE_CUSTOMER);
        object.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        object.setIsDeleted(false);
        return object;
    }

    public User convert(UserChangeRequest request, Long id){
        User old = service.findById(id);
        old.setFirstName(request.getFirstName());
        old.setLastName(request.getLastName());
        old.setAddress(request.getAddress());
        old.setPassportNumber(request.getPassportNumber());
        old.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        return old;
    }

    public UserResponse convert(User user){
        return modelMapper.map(user, UserResponse.class);
    }
}
