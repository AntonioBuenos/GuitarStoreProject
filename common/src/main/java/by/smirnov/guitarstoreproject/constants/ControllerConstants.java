package by.smirnov.guitarstoreproject.constants;

import java.util.Collections;
import java.util.Map;

public interface ControllerConstants {

    String MAPPING_SECURED = "/secured";
    String MAPPING_ADMIN = "/admin";
    String MAPPING_DELETED = "/deleted";
    String MAPPING_HARD_DELETE = "/harddelete";
    String MAPPING_AVG = "/avgstats";
    String MAPPING_ID = "/{id}";
    String MAPPING_EDIT = "/edit";
    String MAPPING_EDIT_BY_ID = MAPPING_ID + MAPPING_EDIT;
    String MAPPING_NEW = "/new";
    String MAPPING_SHOW = "/show";
    String MAPPING_INDEX = "/index";
    String MAPPING_REST = "/rest";
    String REDIRECT = "redirect:";
    String ID = "id";
    String AVG_BY_PRICELIST = "average price-list guitar price";
    String AVG_BY_INSTOCK = "average available in stock guitar price";
    String ERROR_KEY = "Error Message";
    Map<String, String> BAD_LOGIN_MAP = Collections.singletonMap(ERROR_KEY, "Incorrect credentials!");
}
