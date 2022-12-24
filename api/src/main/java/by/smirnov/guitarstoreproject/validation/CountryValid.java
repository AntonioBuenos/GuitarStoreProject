package by.smirnov.guitarstoreproject.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static by.smirnov.guitarstoreproject.validation.ValidationConstants.INVALID_COUNTRY_MESSAGE;

@Documented
@Constraint(validatedBy = {CountryValuesValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CountryValid {

    String message() default INVALID_COUNTRY_MESSAGE;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
