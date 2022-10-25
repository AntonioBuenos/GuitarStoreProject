package by.smirnov.guitarstoreproject.domain;

import by.smirnov.guitarstoreproject.domain.enums.GoodStatus;
import by.smirnov.guitarstoreproject.domain.enums.Placement;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"guitarPosition", "order"})
@EqualsAndHashCode(exclude = {"guitarPosition", "order"})
@Entity
@Table(name = "instock")
public class Instock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Placement placement;

    @Column(name = "good_status")
    @Enumerated(EnumType.STRING)
    private GoodStatus goodStatus;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "modification_date")
    private Timestamp modificationDate;

    @Column(name = "date_sold")
    private Timestamp dateSold;

    @ManyToOne
    @JoinColumn(name = "good_id")
    private Guitar guitarPosition;

    @OneToOne(mappedBy = "instock", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Order order;
}
