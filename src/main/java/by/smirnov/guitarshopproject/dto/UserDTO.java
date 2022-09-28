package by.smirnov.guitarshopproject.dto;

import by.smirnov.guitarshopproject.model.PersonalData;
import by.smirnov.guitarshopproject.model.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class UserDTO {

    //необходимо будет вставить валидаторы
    private long id;
    private String firstName;
    private String lastName;
    private Role role;
    private String login;
    private String password;
    private Timestamp terminationDate;
    private PersonalData personalData;
}
