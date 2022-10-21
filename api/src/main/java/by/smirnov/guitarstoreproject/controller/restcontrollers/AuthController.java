package by.smirnov.guitarstoreproject.controller.restcontrollers;

import by.smirnov.guitarstoreproject.dto.converters.UserConverter;
import by.smirnov.guitarstoreproject.dto.user.AuthChangeRequest;
import by.smirnov.guitarstoreproject.dto.user.AuthRequest;
import by.smirnov.guitarstoreproject.dto.user.AuthResponse;
import by.smirnov.guitarstoreproject.dto.user.UserCreateRequest;
import by.smirnov.guitarstoreproject.model.User;
import by.smirnov.guitarstoreproject.security.JWTUtil;
import by.smirnov.guitarstoreproject.service.RegistrationService;
import by.smirnov.guitarstoreproject.validation.PersonValidator;
import by.smirnov.guitarstoreproject.validation.ValidationErrorConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

import static by.smirnov.guitarstoreproject.constants.AuthControllerConstants.*;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.ID;
import static by.smirnov.guitarstoreproject.constants.ControllerConstants.MAPPING_ID;

@RequiredArgsConstructor
@RestController
@RequestMapping(MAPPING_AUTH)
@Tag(
        name = "User Authentication & Registration",
        description = "User authentication & registration methods"
)
public class AuthController {

    private final RegistrationService registrationService;
    private final PersonValidator personValidator;
    private final JWTUtil jwtUtil;
    private final UserConverter converter;
    private final AuthenticationManager authenticationManager;

    @Operation(
            summary = "User Registration",
            description = "Registers a new user, returns JWT",
            responses = {@ApiResponse(
                    responseCode = "201",
                    description = "User registered"
            )})
    @PostMapping(MAPPING_REGISTRATION)
    public ResponseEntity<?> performRegistration(@RequestBody @Valid UserCreateRequest request,
                                                 BindingResult bindingResult) {

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
        return new ResponseEntity<>(Collections.singletonMap(TOKEN, token), HttpStatus.CREATED);
    }

    @Operation(
            summary = "User Authentication",
            description = "Authenticates user by login and password, returns JWT"
    )
    @PostMapping(MAPPING_LOGIN)
    public ResponseEntity<?> performLogin(@RequestBody AuthRequest request) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(request.getLogin(),
                        request.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(Map.of
                    (SECURITY_ERROR_KEY, SECURITY_ERROR_MESSAGE), HttpStatus.BAD_REQUEST);
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

    @PreAuthorize("hasAnyRole('SALES_CLERC', 'ADMIN') or #userService.findById(#id).getLogin() == principal.username")
    @Operation(
            summary = "Login & password modification",
            description = "Modificates user password and login, returns JWT",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @PatchMapping(MAPPING_ID)
    public ResponseEntity<?> changeCredentials(@PathVariable(ID) long id, @RequestBody @Valid AuthChangeRequest request,
                                               BindingResult bindingResult) {

        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(request.getLogin(),
                        request.getPassword());
        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(Map.of
                    (SECURITY_ERROR_KEY, SECURITY_ERROR_MESSAGE), HttpStatus.BAD_REQUEST);
        }

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        User user = converter.convert(request, id);

        personValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        registrationService.register(user);

        String token = jwtUtil.generateToken(user.getLogin());
        return new ResponseEntity<>(Collections.singletonMap(TOKEN, token), HttpStatus.CREATED);
    }
}
