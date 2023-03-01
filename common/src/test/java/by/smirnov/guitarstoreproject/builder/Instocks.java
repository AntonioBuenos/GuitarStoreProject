package by.smirnov.guitarstoreproject.builder;

import by.smirnov.guitarstoreproject.domain.Instock;
import by.smirnov.guitarstoreproject.domain.enums.GoodStatus;
import by.smirnov.guitarstoreproject.domain.enums.Placement;

import static by.smirnov.guitarstoreproject.builder.Guitars.aGuitar;
import static by.smirnov.guitarstoreproject.constants.TestConstants.TEST_DATE_TIME;
import static by.smirnov.guitarstoreproject.constants.TestConstants.TEST_ID;

public class Instocks {

    public static Instock.InstockBuilder anInstock(){
        return Instock.builder()
                .id(TEST_ID)
                .placement(Placement.STORE)
                .guitarPosition(aGuitar().build())
                .goodStatus(GoodStatus.AVAILABLE)
                .order(null)
                .creationDate(TEST_DATE_TIME);
    }
}
