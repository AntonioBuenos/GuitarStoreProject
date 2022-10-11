package by.smirnov.guitarstoreproject.model;

import by.smirnov.guitarstoreproject.model.enums.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;

import static by.smirnov.guitarstoreproject.model.ValidationConstants.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User implements ObjectEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max=ADDRESS_MAX_SIZE, message = ADDRESS_SIZE_MESSAGE)
    private String address;

    @Column(name = "passport_number")
    @Size(min=PASSPORT_MIN_SIZE, max=PASSPORT_MAX_SIZE, message = PASSPORT_SIZE_MESSAGE)
    private String passportNumber;

    @Column(name = "first_name")
    @NotBlank(message = NO_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=STANDARD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = NO_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=STANDARD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "modification_date")
    private Timestamp modificationDate;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "termination_date")
    private Timestamp terminationDate;

    @NotBlank(message = NO_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=STANDARD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String login;

    @NotBlank(message = NO_BLANK_MESSAGE)
    @Size(min=PASSWORD_MIN_SIZE, max=PASSWORD_MAX_SIZE, message = PASSWORD_SIZE_MESSAGE)
    private String password;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Order> orders;
}
