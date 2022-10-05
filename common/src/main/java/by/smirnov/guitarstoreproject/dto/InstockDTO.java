package by.smirnov.guitarstoreproject.dto;

import by.smirnov.guitarstoreproject.model.enums.GoodStatus;
import by.smirnov.guitarstoreproject.model.enums.Placement;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class InstockDTO implements ObjectDTO{
    private Long id;

    @JsonIgnoreProperties("instockGuitars")
    private GuitarDTO guitarPosition;

    @JsonIgnoreProperties("instock")
    private OrderDTO order;

    private Placement placement;
    private GoodStatus goodStatus;
    private Timestamp dateSold;
}
