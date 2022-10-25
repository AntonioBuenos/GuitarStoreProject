package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.model.PersonDetails;
import by.smirnov.guitarstoreproject.model.User;
import by.smirnov.guitarstoreproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static by.smirnov.guitarstoreproject.constants.CommonConstants.USER_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class UserDetailsSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(username);

        if (user.isEmpty()) throw new UsernameNotFoundException(USER_NOT_FOUND);

        return new PersonDetails(user.get());
    }
}