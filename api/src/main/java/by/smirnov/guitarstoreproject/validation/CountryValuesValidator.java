package by.smirnov.guitarstoreproject.validation;

import com.neovisionaries.i18n.CountryCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CountryValuesValidator implements ConstraintValidator<CountryValid, String> {

    @Override
    public boolean isValid(String valueForValidation, ConstraintValidatorContext constraintValidatorContext) {

        boolean result = false;

        if (valueForValidation == null) return true;
        else if (CountryCode.getByCodeIgnoreCase(valueForValidation) != null) result = true;
        else if (CountryCode.valueOf(valueForValidation) != null) result = true;

        return result;
    }
}
