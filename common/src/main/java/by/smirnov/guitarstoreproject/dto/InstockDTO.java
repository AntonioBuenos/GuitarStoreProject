package by.smirnov.guitarstoreproject.dto;

import by.smirnov.guitarstoreproject.model.enums.GoodStatus;
import by.smirnov.guitarstoreproject.model.enums.Placement;

import java.sql.Timestamp;

public class InstockDTO implements ObjectDTO{
    private Long id;
    private Long goodId;
    private Placement placement;
    private GoodStatus goodStatus;
    private Timestamp dateSold;
}
