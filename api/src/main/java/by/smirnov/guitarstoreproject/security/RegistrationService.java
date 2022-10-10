package by.smirnov.guitarstoreproject.security;

import by.smirnov.guitarstoreproject.model.User;
import by.smirnov.guitarstoreproject.model.enums.Role;
import by.smirnov.guitarstoreproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class RegistrationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(User object){
        object.setPassword(passwordEncoder.encode(object.getPassword()));
        object.setRole(Role.ROLE_CUSTOMER);
        object.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        object.setIsDeleted(false);
        repository.save(object);
    }
}
