package by.smirnov.guitarstoreproject.controller.restcontrollers;

import by.smirnov.guitarstoreproject.domain.User;
import by.smirnov.guitarstoreproject.dto.converters.UserConverter;
import by.smirnov.guitarstoreproject.dto.user.AuthChangeRequest;
import by.smirnov.guitarstoreproject.dto.user.AuthRequest;
import by.smirnov.guitarstoreproject.dto.user.AuthResponse;
import by.smirnov.guitarstoreproject.dto.user.UserCreateRequest;
import by.smirnov.guitarstoreproject.dto.user.UserResponse;
import by.smirnov.guitarstoreproject.security.AuthChecker;
import by.smirnov.guitarstoreproject.security.JWTUtil;
import by.smirnov.guitarstoreproject.security.RegistrationService;
import by.smirnov.guitarstoreproject.service.UserService;
import by.smirnov.guitarstoreproject.validation.PersonValidator;
import by.smirnov.guitarstoreproject.validation.ValidationErrorConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Map;
import java.util.Objects;

import static by.smirnov.guitarstoreproject.constants.CommonConstants.BAD_LOGIN_MAP;
import static by.smirnov.guitarstoreproject.constants.CommonConstants.ID;
import static by.smirnov.guitarstoreproject.constants.CommonConstants.MAPPING_AUTH;
import static by.smirnov.guitarstoreproject.constants.CommonConstants.MAPPING_ID;
import static by.smirnov.guitarstoreproject.constants.CommonConstants.MAPPING_LOGIN;
import static by.smirnov.guitarstoreproject.constants.CommonConstants.MAPPING_REGISTRATION;
import static by.smirnov.guitarstoreproject.constants.CommonConstants.MAPPING_VERIFY;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.ALREADY_DELETED_MAP;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.CODE;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.FORBIDDEN_MAP;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.NOT_VERIFIED_MAP;
import static by.smirnov.guitarstoreproject.controller.restcontrollers.ControllerConstants.VERIFIED;

@RequiredArgsConstructor
@RestController
@RequestMapping(MAPPING_AUTH)
@Tag(
        name = "User Authentication & Registration",
        description = "All user security methods: authentication, registration, " +
                "user credentials change & e-mail verification."
)
public class AuthRestController {

    private final RegistrationService service;
    private final PersonValidator personValidator;
    private final JWTUtil jwtUtil;
    private final UserConverter converter;
    private final AuthenticationManager authenticationManager;
    private final AuthChecker authChecker;
    private final UserService userService;

    @Operation(
            summary = "User Registration",
            description = "Registers a new user. Starts e-mail verification " +
                    "(sends e-mail to a new user). New user role is always CUSTOMER " +
                    "(to be changed by ADMIN only).",
            responses = {@ApiResponse(
                    responseCode = "201",
                    description = "User registered"
            )})
    @PostMapping(MAPPING_REGISTRATION)
    public ResponseEntity<?> performRegistration(
            @RequestBody @Valid UserCreateRequest request,
            BindingResult bindingResult,
            HttpServletRequest httpServletRequest)
            throws MessagingException, UnsupportedEncodingException {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        User user = converter.convert(request);
        personValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        User registered = service.register(user);
        UserResponse response = converter.convert(registered);

        service.sendVerificationEmail(user, getSiteURL(httpServletRequest));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "User Authentication",
            description = "Authenticates user by login and password, returns JWT " +
                    "if e-mail verification procedure has been passed by the customer."
    )
    @PostMapping(MAPPING_LOGIN)
    public ResponseEntity<?> performLogin(@RequestBody AuthRequest request) {

        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(request.getLogin(),
                        request.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(BAD_LOGIN_MAP, HttpStatus.BAD_REQUEST);
        }

        User user = userService.findByLogin(request.getLogin());
        if(Boolean.FALSE.equals(user.getIsEnabled())) {
            return new ResponseEntity<>(NOT_VERIFIED_MAP, HttpStatus.FORBIDDEN);
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

    @Operation(
            summary = "Login & password modification",
            description = "Modifies user password and login, returns JWT",
            security = {@SecurityRequirement(name = "JWT Bearer")}
    )
    @PutMapping(MAPPING_ID)
    public ResponseEntity<?> changeCredentials(@PathVariable(ID) long id,
                                               @RequestBody @Valid AuthChangeRequest request,
                                               BindingResult bindingResult,
                                               Principal principal) {

        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
                request.getLogin(), request.getPassword());
        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(BAD_LOGIN_MAP, HttpStatus.BAD_REQUEST);
        }

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        if (Objects.isNull(principal) || authChecker.isAuthorized(principal.getName(), id)) {
            return new ResponseEntity<>(FORBIDDEN_MAP, HttpStatus.FORBIDDEN);
        }

        User user = converter.convert(request, id);
        if (Boolean.TRUE.equals(user.getIsDeleted())) {
            return new ResponseEntity<>(ALREADY_DELETED_MAP, HttpStatus.NOT_MODIFIED);
        }

        personValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ValidationErrorConverter.getErrors(bindingResult);
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        service.register(user);

        String token = jwtUtil.generateToken(user.getLogin());
        return ResponseEntity.ok(
                AuthResponse
                        .builder()
                        .login(request.getLogin())
                        .token(token)
                        .build()
        );
    }

    @Operation(
            summary = "User's e-mail sent code verification",
            description = "Verifies an UUID code sent toa user's e-mail inside a " +
                    "hyper-reference to a newly-registered user."
    )
    @GetMapping(MAPPING_VERIFY)
    public ResponseEntity<?> verifyUser(@Param(CODE) String code) {
        if (service.verify(code)) {
            return new ResponseEntity<>(VERIFIED, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(NOT_VERIFIED_MAP, HttpStatus.FORBIDDEN);
        }
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
