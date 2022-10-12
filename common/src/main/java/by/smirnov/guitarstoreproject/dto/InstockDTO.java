package by.smirnov.guitarstoreproject.dto;

import by.smirnov.guitarstoreproject.model.enums.GoodStatus;
import by.smirnov.guitarstoreproject.model.enums.Placement;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.sql.Timestamp;

import static by.smirnov.guitarstoreproject.model.ValidationConstants.NOT_BLANK_MESSAGE;
import static by.smirnov.guitarstoreproject.model.ValidationConstants.NULL_MESSAGE;

@Getter
@Setter
public class InstockDTO implements ObjectDTO{

    @Null(message = NULL_MESSAGE)
    private Long id;

    @JsonIgnoreProperties("instockGuitars")
    private GuitarDTO guitarPosition;

    @JsonIgnoreProperties("instock")
    private OrderDTO order;

    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Enumerated(EnumType.STRING)
    private Placement placement;

    @Null(message = NULL_MESSAGE)
    @Enumerated(EnumType.STRING)
    private GoodStatus goodStatus;

    @Null(message = NULL_MESSAGE)
    private Timestamp dateSold;
}
