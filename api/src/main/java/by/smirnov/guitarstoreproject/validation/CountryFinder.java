package by.smirnov.guitarstoreproject.validation;

import com.neovisionaries.i18n.CountryCode;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CountryFinder {

    public static CountryCode getCountryCode(String nameOrCode){
        CountryCode code;
        if ((code = CountryCode.getByCodeIgnoreCase(nameOrCode)) != null) return code;
        try{
            code = CountryCode.valueOf(nameOrCode);
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
        return code;
    }
}
