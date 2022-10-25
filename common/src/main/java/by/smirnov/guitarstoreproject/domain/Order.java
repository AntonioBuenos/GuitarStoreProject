package by.smirnov.guitarstoreproject.domain;

import by.smirnov.guitarstoreproject.domain.enums.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"customer", "instock"})
@EqualsAndHashCode(exclude = {"customer", "instock"})
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "modification_date")
    private Timestamp modificationDate;

    @Column(name = "termination_date")
    private Timestamp terminationDate;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User customer;

    @OneToOne
    @JoinColumn(name = "instock_id", referencedColumnName = "id")
    private Instock instock;
}
