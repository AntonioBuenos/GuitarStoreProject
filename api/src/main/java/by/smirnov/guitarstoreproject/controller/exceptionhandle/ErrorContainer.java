package by.smirnov.guitarstoreproject.controller.exceptionhandle;

import lombok.*;

@Setter
@Getter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorContainer {

    private String exceptionId;

    private String errorMessage;

    private String e;

    private String time;
}
