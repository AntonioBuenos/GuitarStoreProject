package by.smirnov.guitarstoreproject.security;

import by.smirnov.guitarstoreproject.model.User;
import by.smirnov.guitarstoreproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class AuthenticatedUserService {

    @Autowired
    private UserRepository userRepository;

    public boolean hasId(Long id){
        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getName();
        User user = userRepository.findByLogin(username).orElse(null);
        return user.getId().equals(id);
    }
}
