package by.smirnov.guitarstoreproject.validation;

import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Locale;

@RequiredArgsConstructor
public class CountryValuesValidator implements ConstraintValidator<CountryValid, String> {

    private final CountryFinder finder;

    @Override
    public boolean isValid(String valueForValidation, ConstraintValidatorContext constraintValidatorContext) {

        return finder.getCountryCode(valueForValidation) != null;
    }
}
