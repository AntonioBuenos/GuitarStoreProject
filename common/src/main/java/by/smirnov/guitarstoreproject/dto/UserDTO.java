package by.smirnov.guitarstoreproject.dto;

import by.smirnov.guitarstoreproject.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.util.List;

@Data
@ToString
@EqualsAndHashCode
public class UserDTO implements ObjectDTO{

    //необходимо будет вставить валидаторы
    private Long id;
    private String firstName;
    private String lastName;
    private Role role;
    private String login;
    private String password;
    private Timestamp terminationDate;
    private String address;
    private String passportNumber;
    
    @JsonIgnoreProperties("customer")
    private List<OrderDTO> orders;
}
