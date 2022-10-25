package by.smirnov.guitarstoreproject.domain;

import com.neovisionaries.i18n.CountryCode;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"manufacturer", "guitarGenres", "instockGuitars"})
@EqualsAndHashCode(exclude = {"manufacturer", "guitarGenres", "instockGuitars"})
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
    private GuitarManufacturer manufacturer;

    @ManyToMany(mappedBy = "byGenreGuitars", fetch = FetchType.LAZY)
    private Set<Genre> guitarGenres;

    @OneToMany(mappedBy = "guitarPosition", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Instock> instockGuitars;
}
