package by.smirnov.guitarstoreproject.model;

public interface ValidationConstants {
    String NO_BLANK_MESSAGE = "'${validatedValue}' cannot be empty or blank";
    int STANDARD_MIN_SIZE = 2;
    int STANDARD_MAX_SIZE = 20;
    String STANDARD_SIZE_MESSAGE = "'${validatedValue}' length must be between {min} and {max} characters long";
    int PASSPORT_MAX_SIZE = 10;
    String MAX_SIZE_MESSAGE = "'${validatedValue} length must be up to {max} characters long";
    int PASSWORD_MIN_SIZE = 2;
    int PASSWORD_MAX_SIZE = STANDARD_MAX_SIZE;
    int ADDRESS_MAX_SIZE = 50;
}
