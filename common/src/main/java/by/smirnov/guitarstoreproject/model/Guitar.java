package by.smirnov.guitarstoreproject.model;

import com.neovisionaries.i18n.CountryCode;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "guitars")
public class Guitar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String typeof;

    private String shape;

    private String series;

    private String model;

    @Column(name = "strings_qnt")
    private int stringsQnt;

    private String neck;

    private String bridge;

    @Column(name = "body_material")
    private String bodyMaterial;

    private double price;

    @Column(name = "prod_country")
    @Enumerated(EnumType.STRING)
    private CountryCode prodCountry;

    @Column(name = "brand_id")
    private long brandId;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "modification_date")
    private Timestamp modificationDate;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "termination_date")
    private Timestamp terminationDate;

}
