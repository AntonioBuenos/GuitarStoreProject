package by.smirnov.guitarstoreproject.security;

import by.smirnov.guitarstoreproject.dto.AuthenticationDTO;
import by.smirnov.guitarstoreproject.dto.UserDTO;
import by.smirnov.guitarstoreproject.model.User;
import by.smirnov.guitarstoreproject.service.RegistrationService;
import by.smirnov.guitarstoreproject.util.EntityDTOConverter;
import by.smirnov.guitarstoreproject.validation.PersonValidator;
import by.smirnov.guitarstoreproject.validation.ValidationErrorConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final PersonValidator personValidator;
    private final JWTUtil jwtUtil;
    private final EntityDTOConverter entityDTOConverter;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/registration")
    public ResponseEntity<?> performRegistration(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult){

        User user = (User) entityDTOConverter.convertToEntity(userDTO, User.class);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        personValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }
        registrationService.register(user);

        String token = jwtUtil.generateToken(user.getLogin());
        return new ResponseEntity<>(Collections.singletonMap("jwt-token", token), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthenticationDTO authenticationDTO){
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getLogin(),
                        authenticationDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e){
            return Map.of("Message", "Incorrect credentials!");
        }

        String token = jwtUtil.generateToken(authenticationDTO.getLogin());
        return Map.of("jwt-token", token);
    }
}