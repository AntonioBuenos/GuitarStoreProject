package by.smirnov.guitarstoreproject.model;

import by.smirnov.guitarstoreproject.model.enums.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;

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

    @Size(max=30, message = "Address shall be up to 30 characters")
    private String address;

    @Column(name = "passport_number")
    @Size(min=7, max=10, message = "Passport number length shall match 7-10 characters")
    private String passportNumber;

    @Column(name = "first_name")
    @NotBlank(message = "First name cannot be empty or blank")
    @Size(min=2, max=20, message = "First name length shall match 2-20 characters")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Last name cannot be empty or blank")
    @Size(min=2, max=20, message = "Last name length shall match 2-20 characters")
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

    @NotBlank(message = "Login cannot be empty or blank")
    @Size(min=2, max=30, message = "Login length shall match 2-30 characters")
    private String login;

    @NotBlank(message = "Password cannot be empty or blank")
    @Size(min=8, max=20, message = "Password length shall match 8-20 characters")
    private String password;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Order> orders;
}
