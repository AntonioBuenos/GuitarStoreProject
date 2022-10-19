package by.smirnov.guitarstoreproject.model;

import by.smirnov.guitarstoreproject.model.enums.GoodStatus;
import by.smirnov.guitarstoreproject.model.enums.Placement;
import lombok.*;
import org.hibernate.annotations.Cascade;

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
public class Instock implements ObjectEntity {

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

    @OneToOne(mappedBy = "instock")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Order order;
}
