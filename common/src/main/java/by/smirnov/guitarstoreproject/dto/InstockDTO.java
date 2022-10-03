package by.smirnov.guitarstoreproject.dto;

import by.smirnov.guitarstoreproject.model.enums.GoodStatus;
import by.smirnov.guitarstoreproject.model.enums.Placement;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class InstockDTO implements ObjectDTO{
    private Long id;
    private Long goodId;
    private Placement placement;
    private GoodStatus goodStatus;
    private Timestamp dateSold;
}
