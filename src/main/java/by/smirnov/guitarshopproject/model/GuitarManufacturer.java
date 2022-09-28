package by.smirnov.guitarshopproject.model;

import com.neovisionaries.i18n.CountryCode;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "guitar_manufacturer")
public class GuitarManufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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
    private boolean isDeleted;

    @Column(name = "termination_date")
    private Timestamp terminationDate;
}
