package by.smirnov.guitarstoreproject.model;

public interface ValidationConstants {
    String NO_BLANK_MESSAGE = "This value cannot be empty or blank";
    int STANDARD_MIN_SIZE = 2;
    int STANDARD_MAX_SIZE = 20;
    String STANDARD_SIZE_MESSAGE = "This value length shall match " + STANDARD_MIN_SIZE + "-" + STANDARD_MAX_SIZE + " characters";
    int PASSPORT_MIN_SIZE = 7;
    int PASSPORT_MAX_SIZE = 10;
    String PASSPORT_SIZE_MESSAGE = "This value length shall match " + PASSPORT_MIN_SIZE + "-" + PASSPORT_MAX_SIZE + " characters";
    int PASSWORD_MIN_SIZE = 2;
    int PASSWORD_MAX_SIZE = STANDARD_MAX_SIZE;
    String PASSWORD_SIZE_MESSAGE = "This value length shall match " + PASSWORD_MIN_SIZE + "-" + PASSWORD_MAX_SIZE + " characters";
    int ADDRESS_MAX_SIZE = 50;
    String ADDRESS_SIZE_MESSAGE = "Address length shall match up to " + ADDRESS_MAX_SIZE + " characters";
}
