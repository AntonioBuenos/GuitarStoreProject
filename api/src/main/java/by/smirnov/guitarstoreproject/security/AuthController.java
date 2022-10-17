package by.smirnov.guitarstoreproject.security;

import by.smirnov.guitarstoreproject.dto.user.AuthRequest;
import by.smirnov.guitarstoreproject.dto.converters.UserConverter;
import by.smirnov.guitarstoreproject.dto.user.AuthResponse;
import by.smirnov.guitarstoreproject.dto.user.UserCreateRequest;
import by.smirnov.guitarstoreproject.model.User;
import by.smirnov.guitarstoreproject.service.RegistrationService;
import by.smirnov.guitarstoreproject.validation.PersonValidator;
import by.smirnov.guitarstoreproject.validation.ValidationErrorConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "User Authentication & Registration", description = "User authentication & registration methods")
public class AuthController {

    private final RegistrationService registrationService;
    private final PersonValidator personValidator;
    private final JWTUtil jwtUtil;
    private final UserConverter converter;
    private final AuthenticationManager authenticationManager;

    @Operation(
            summary = "User Registration",
            description = "Registers a new user, returns JWT",
            responses = {@ApiResponse(responseCode = "201", description = "User registered")})
    @PostMapping("/registration")
    public ResponseEntity<?> performRegistration(@RequestBody @Valid UserCreateRequest request,
                                                 BindingResult bindingResult){

        User user = converter.convert(request);

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

    @Operation(
            summary = "User Authentication",
            description = "Authenticates user by login and password, returns JWT")
    @PostMapping("/login")
    public ResponseEntity<?> performLogin(@RequestBody AuthRequest request){
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(request.getLogin(),
                        request.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e){
            return new ResponseEntity<>(Map.of("ErrorMessage", "Incorrect credentials!"), HttpStatus.BAD_REQUEST);
        }

        String token = jwtUtil.generateToken(request.getLogin());
        return ResponseEntity.ok(
                AuthResponse
                        .builder()
                        .login(request.getLogin())
                        .token(token)
                        .build()
        );
    }
}
