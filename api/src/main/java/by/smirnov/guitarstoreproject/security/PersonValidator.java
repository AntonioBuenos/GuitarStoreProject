package by.smirnov.guitarstoreproject.security;

import by.smirnov.guitarstoreproject.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Component
public class PersonValidator implements Validator {

    private final UserDetailsSecurityService service;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        try {
            service.loadUserByUsername(user.getLogin());
        }catch (UsernameNotFoundException ignored){
            return; // если пользователь не найден - все ок. Это плохой код (с опорой на исключение).
            // Нужен свой сервис с Optional, а здесь - проверка есть ли человек в Optional.
        }

        errors.rejectValue("login", "",
                "Человек с таким именем уже существует, выберите другой логин");
    }


}
