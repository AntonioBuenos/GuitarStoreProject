package by.smirnov.guitarstoreproject.security;

import by.smirnov.guitarstoreproject.domain.User;
import by.smirnov.guitarstoreproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static by.smirnov.guitarstoreproject.constants.ResponseEntityConstants.USER_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class UserDetailsSecurityServiceImpl implements UserDetailsSecurityService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(username);

        if (user.isEmpty()) throw new UsernameNotFoundException(USER_NOT_FOUND);

        return new PersonDetails(user.get());
    }
}