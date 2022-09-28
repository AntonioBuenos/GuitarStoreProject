package by.smirnov.guitarstoreproject.model;

import by.smirnov.guitarstoreproject.model.enums.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "instock_id")
    private String instockId;

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
}
