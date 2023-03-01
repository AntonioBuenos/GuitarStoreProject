package by.smirnov.guitarstoreproject.builder;

import by.smirnov.guitarstoreproject.domain.User;
import by.smirnov.guitarstoreproject.domain.enums.Role;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static by.smirnov.guitarstoreproject.constants.TestConstants.TEST_ADDRESS;
import static by.smirnov.guitarstoreproject.constants.TestConstants.TEST_DATE_TIME;
import static by.smirnov.guitarstoreproject.constants.TestConstants.TEST_ID;
import static by.smirnov.guitarstoreproject.constants.TestConstants.TEST_STRING;

public class Users {

    public static User.UserBuilder aUser(){
        return User.builder()
                .id(TEST_ID)
                .address(TEST_ADDRESS)
                .passportNumber("1234567890")
                .email("test@test.by")
                .role(Role.ROLE_CUSTOMER)
                .firstName(TEST_STRING)
                .lastName(TEST_STRING)
                .login(TEST_STRING)
                .password(TEST_STRING)
                .verificationCode(null)
                .isEnabled(true)
                .orders(null)
                .isDeleted(false)
                .creationDate(TEST_DATE_TIME);
    }
}
