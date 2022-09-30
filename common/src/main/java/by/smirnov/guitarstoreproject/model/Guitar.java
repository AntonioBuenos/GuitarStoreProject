package by.smirnov.guitarstoreproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private Long id;

    private String typeof;

    private String shape;

    private String series;

    private String model;

    @Column(name = "strings_qnt")
    private Integer stringsQnt;

    private String neck;

    private String bridge;

    @Column(name = "body_material")
    private String bodyMaterial;

    private Double price;

    @Column(name = "prod_country")
    @Enumerated(EnumType.STRING)
    private CountryCode prodCountry;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "modification_date")
    private Timestamp modificationDate;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "termination_date")
    private Timestamp terminationDate;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    @JsonBackReference
    private GuitarManufacturer manufacturer;

}
