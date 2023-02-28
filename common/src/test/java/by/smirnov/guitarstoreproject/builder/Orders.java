package by.smirnov.guitarstoreproject.builder;

import by.smirnov.guitarstoreproject.domain.Order;
import by.smirnov.guitarstoreproject.domain.enums.OrderStatus;

import static by.smirnov.guitarstoreproject.constants.TestConstants.ID;

public class Orders {

    public static Order.OrderBuilder anOrder(){
        return Order.builder()
                .id(ID)
                .deliveryAddress("test")
                .orderStatus(OrderStatus.CREATED);
    }
}
