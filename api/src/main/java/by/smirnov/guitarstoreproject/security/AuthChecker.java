package by.smirnov.guitarstoreproject.security;

import by.smirnov.guitarstoreproject.model.User;
import by.smirnov.guitarstoreproject.model.enums.Role;
import by.smirnov.guitarstoreproject.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class AuthChecker {

    private final UserService service;

    public boolean isAuthorized(String login, Long id){
        User authenticatedUser = service.findByLogin(login);
        Role authUserRole = authenticatedUser.getRole();
        if(authUserRole == Role.ROLE_ADMIN || authUserRole == Role.ROLE_SALES_CLERC){
            return false;
        }
        Long authenticatedId = authenticatedUser.getId();
        return !Objects.equals(id, authenticatedId);
    }
}
