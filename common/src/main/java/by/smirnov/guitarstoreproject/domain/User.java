package by.smirnov.guitarstoreproject.domain;

import by.smirnov.guitarstoreproject.domain.enums.Role;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"orders"})
@EqualsAndHashCode(exclude = {"orders"})
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
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

    private String login;

    private String password;

    private String email;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Order> orders;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "verification_code")
    private String verificationCode;
}
