package by.smirnov.guitarstoreproject.model;

public interface ValidationConstants {
    String NOT_BLANK_MESSAGE = "This value cannot be empty or blank";
    int STANDARD_MIN_SIZE = 2;
    int STANDARD_MAX_SIZE = 20;
    String STANDARD_SIZE_MESSAGE = "This value length must be between {min} and {max} characters long. Your input is: ${validatedValue}";
    int PASSPORT_MAX_SIZE = 10;
    String MAX_SIZE_MESSAGE = "This value length must be up to {max} characters long. Your input is: ${validatedValue}";
    int PASSWORD_MIN_SIZE = 2;
    int PASSWORD_MAX_SIZE = STANDARD_MAX_SIZE;
    int ADDRESS_MAX_SIZE = 50;
    int EXTENDED_MAX_SIZE = 30;
    String NULL_MESSAGE = "You should not input this value, it will be assigned automatically";
    String NOT_NULL_MESSAGE = "This value cannot be null";
    String NOT_NEGATIVE_MESSAGE = "This value cannot be negative";
}
