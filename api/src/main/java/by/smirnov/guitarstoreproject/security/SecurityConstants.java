package by.smirnov.guitarstoreproject.security;

public interface SecurityConstants {
    String AUTH_HEADER_NAME = "Authorization";
    String AUTH_HEADER_STARTS = "Bearer ";
    String INVALID_TOKEN_MESSAGE = "Invalid JWT Token";
    String INVALID_HEADER_TOKEN_MESSAGE = "Invalid JWT Token in Bearer Header";
    String VALIDATION_ERROR_MESSAGE = "User with this login already registered, choose another unique login";
    String JWT_SUBJECT = "User details";
    String CLAIM_NAME = "login";
    String LOGIN = "login";
    String ISSUER = "GuitarStoreApp";
}
