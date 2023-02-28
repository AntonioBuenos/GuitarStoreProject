package by.smirnov.guitarstoreproject.builder;

import by.smirnov.guitarstoreproject.domain.User;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static by.smirnov.guitarstoreproject.constants.TestConstants.ID;

public class Users {

    public static User.UserBuilder aUser(){
        return User.builder()
                .id(ID)
                .address("City")
                .creationDate(Timestamp.valueOf(LocalDateTime.of(LocalDate.of(2023,1,1), LocalTime.MIN)))
                .email("test@test.by")
                .firstName("test")
                .lastName("test")
                .login("test")
                .password("test")
                .isEnabled(true)
                .isDeleted(false);
    }
}
