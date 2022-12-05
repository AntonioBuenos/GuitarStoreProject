package by.smirnov.guitarstoreproject.validation;

import com.neovisionaries.i18n.CountryCode;
import org.springframework.stereotype.Component;

@Component
public class CountryFinder {

    public CountryCode getCountryCode(String nameOrCode) {
        CountryCode code;
        if ((code = CountryCode.getByCodeIgnoreCase(nameOrCode)) != null) return code;
        for (CountryCode value : CountryCode.values()) {
            if (value.getName().equalsIgnoreCase(nameOrCode)) {
                return value;
            }
        }
        return null;
    }
}
