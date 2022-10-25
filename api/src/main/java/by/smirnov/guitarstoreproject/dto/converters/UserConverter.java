package by.smirnov.guitarstoreproject.dto.converters;

import by.smirnov.guitarstoreproject.dto.user.AuthChangeRequest;
import by.smirnov.guitarstoreproject.dto.user.UserChangeRequest;
import by.smirnov.guitarstoreproject.dto.user.UserCreateRequest;
import by.smirnov.guitarstoreproject.dto.user.UserResponse;
import by.smirnov.guitarstoreproject.domain.User;
import by.smirnov.guitarstoreproject.domain.enums.Role;
import by.smirnov.guitarstoreproject.service.UserService;
import by.smirnov.guitarstoreproject.util.UUIDGenerator;
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
        User created = modelMapper.map(request, User.class);
        created.setRole(Role.ROLE_CUSTOMER);
        created.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        created.setIsDeleted(false);
        created.setIsEnabled(false);
        created.setVerificationCode(UUIDGenerator.generateUUID());
        return created;
    }

    public User convert(UserChangeRequest request, Long id){
        User old = service.findById(id);
        old.setFirstName(request.getFirstName());
        old.setLastName(request.getLastName());
        old.setAddress(request.getAddress());
        old.setPassportNumber(request.getPassportNumber());
        old.setEmail(request.getEmail());
        old.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        return old;
    }

    public User convert(AuthChangeRequest request, Long id){
        User old = service.findById(id);
        old.setLogin(request.getNewLogin());
        old.setPassword(request.getNewPassword());
        old.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        return old;
    }

    public UserResponse convert(User user){
        return modelMapper.map(user, UserResponse.class);
    }
}
