package by.smirnov.guitarstoreproject.constants;

import java.util.Collections;
import java.util.Map;

public interface CommonConstants {

    String MAPPING_SECURED = "/secured";
    String MAPPING_DELETED = "/deleted";
    String MAPPING_AVG = "/avgstats";
    String MAPPING_ID = "/{id}";
    String MAPPING_VERIFY = "/verify";
    String MAPPING_REST = "/rest";
    String ID = "id";
    String AVG_BY_PRICELIST = "average price-list guitar price";
    String AVG_BY_INSTOCK = "average available in stock guitar price";
    String ERROR_KEY = "Error Message";
    Map<String, String> BAD_LOGIN_MAP = Collections.singletonMap(ERROR_KEY, "Incorrect credentials!");
    String USER_NOT_FOUND = "User not found!";
    String MAPPING_AUTH = "/auth";
    String MAPPING_REGISTRATION = "/registration";
    String MAPPING_LOGIN = "/login";
    String TOKEN = "jwt-token";
    String AVG_FORMAT = "%.2f $";
}
