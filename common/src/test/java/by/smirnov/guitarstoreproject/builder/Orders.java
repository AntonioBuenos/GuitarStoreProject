package by.smirnov.guitarstoreproject.builder;

import by.smirnov.guitarstoreproject.domain.Order;
import by.smirnov.guitarstoreproject.domain.enums.OrderStatus;

import static by.smirnov.guitarstoreproject.builder.Users.aUser;
import static by.smirnov.guitarstoreproject.constants.TestConstants.TEST_ADDRESS;
import static by.smirnov.guitarstoreproject.constants.TestConstants.TEST_DATE_TIME;
import static by.smirnov.guitarstoreproject.constants.TestConstants.TEST_ID;

public class Orders {

    public static Order.OrderBuilder anOrder(){
        return Order.builder()
                .id(TEST_ID)
                .instock(null)
                .deliveryAddress(TEST_ADDRESS)
                .customer(aUser().build())
                .orderStatus(OrderStatus.CREATED)
                .creationDate(TEST_DATE_TIME);
    }
}
