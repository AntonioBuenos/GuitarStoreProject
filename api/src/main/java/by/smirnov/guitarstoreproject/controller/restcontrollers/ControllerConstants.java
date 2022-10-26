package by.smirnov.guitarstoreproject.controller.restcontrollers;

import java.util.Collections;
import java.util.Map;

import static by.smirnov.guitarstoreproject.constants.CommonConstants.ERROR_KEY;
import static by.smirnov.guitarstoreproject.constants.CommonConstants.ID;

public interface ControllerConstants {

    Map<String, String> NOT_FOUND_MAP = Collections.singletonMap(
            ERROR_KEY,
            "Wrong ID, object not found");
    Map<String, String> FORBIDDEN_MAP = Collections.singletonMap(
            ERROR_KEY,
            "Authorization check failed");
    Map<String, String> ALREADY_DELETED_MAP = Collections.singletonMap(
            ERROR_KEY,
            "Object by this ID is already deleted");
    Map<String, String> NOT_VERIFIED_MAP = Collections.singletonMap(
            ERROR_KEY,
            "Verification failed, wrong email verification code");
    Map<String, String> BAD_INSTOCK_MAP = Collections.singletonMap(
            ERROR_KEY,
            "There is no available for order instock good by this ID");
    Map<String, String> BAD_CUSTOMER_MAP = Collections.singletonMap(
            ERROR_KEY,
            "Customer account by this ID does not exist or is deleted");
    Map<String, String> BAD_GUITAR_MAP = Collections.singletonMap(
            ERROR_KEY,
            "There is no guitar in price-list by this ID");
    Map<String, String> BAD_BRAND_MAP = Collections.singletonMap(
            ERROR_KEY,
            "Guitar manufacturer by this ID does not exist or is deleted");
    Map<String, String> BAD_GENRE_MAP = Collections.singletonMap(
            ERROR_KEY,
            "At least one of the genres by input IDs does not exist or is deleted");
    String PAGE_SORT = ID;
    int PAGE_SIZE = 20;
}
