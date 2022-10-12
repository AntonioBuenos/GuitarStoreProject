package by.smirnov.guitarstoreproject.model;

import com.neovisionaries.i18n.CountryCode;
import lombok.*;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "guitar_manufacturer")
@Cacheable("guitarManufacturer")
public class GuitarManufacturer implements ObjectEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;

    private String company;

    @Column(name = "origin_country")
    @Enumerated(EnumType.STRING)
    private CountryCode originCountry;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "modification_date")
    private Timestamp modificationDate;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "termination_date")
    private Timestamp terminationDate;

    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Guitar> guitars;
}
