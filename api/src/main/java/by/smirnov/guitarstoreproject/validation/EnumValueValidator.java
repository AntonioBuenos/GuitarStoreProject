package by.smirnov.guitarstoreproject.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValueValidator implements ConstraintValidator<EnumValid, String> {

    private EnumValid annotation;

    @Override
    public void initialize(EnumValid annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String valueForValidation, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;

        if (valueForValidation == null)
            return true;

        Object[] enumValues = this.annotation.enumClass().getEnumConstants();

        if (enumValues != null) {
            for (Object enumValue : enumValues) {
                if (valueForValidation.equals(((java.lang.Enum<?>) enumValue).name())) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }
}
