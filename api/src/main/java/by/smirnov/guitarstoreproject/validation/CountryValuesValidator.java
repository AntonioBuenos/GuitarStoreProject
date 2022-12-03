package by.smirnov.guitarstoreproject.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CountryValuesValidator implements ConstraintValidator<CountryValid, String> {

    @Override
    public boolean isValid(String valueForValidation, ConstraintValidatorContext constraintValidatorContext) {

        return CountryFinder.getCountryCode(valueForValidation) != null;
    }
}
