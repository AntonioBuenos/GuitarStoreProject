package by.smirnov.guitarstoreproject.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static by.smirnov.guitarstoreproject.validation.ValidationConstants.INVALID_ENUM_MESSAGE;

@Documented
@Constraint(validatedBy = {EnumValueValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValid {

    String message() default INVALID_ENUM_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends java.lang.Enum<?>> enumClass();
}
