package by.smirnov.guitarstoreproject.constants;

import java.util.Collections;
import java.util.Map;

public interface ResponseEntityConstants {
    String ERROR_KEY = "Error Message";
    Map<String, String> BAD_STATUS_MAP = Collections.singletonMap(
            ERROR_KEY,
            "Entity cannot be changed due to its status");
    Map<String, String> BAD_GENRE_MAP = Collections.singletonMap(
            ERROR_KEY,
            "At least one of the genres by these IDs does not exist or is deleted");
    Map<String, String> BAD_BRAND_MAP = Collections.singletonMap(
            ERROR_KEY,
            "Guitar manufacturer by this ID does not exist or is deleted");
    Map<String, String> BAD_GUITAR_MAP = Collections.singletonMap(
            ERROR_KEY,
            "There is no guitar in price-list by this ID");
    Map<String, String> BAD_CUSTOMER_MAP = Collections.singletonMap(
            ERROR_KEY,
            "Customer account by this ID does not exist or is deleted");
    Map<String, String> BAD_INSTOCK_MAP = Collections.singletonMap(
            ERROR_KEY,
            "There is no available for order instock good by this ID");
    Map<String, String> NOT_VERIFIED_MAP = Collections.singletonMap(
            ERROR_KEY,
            "You did non pass email verification or Your email verification code is wrong");
    Map<String, String> ALREADY_DELETED_MAP = Collections.singletonMap(
            ERROR_KEY,
            "Object by this ID is already deleted");
    Map<String, String> FORBIDDEN_MAP = Collections.singletonMap(
            ERROR_KEY,
            "Authorization check failed");
    Map<String, String> NOT_FOUND_MAP = Collections.singletonMap(
            ERROR_KEY,
            "Wrong ID, object not found");
    Map<String, String> BAD_LOGIN_MAP = Collections.singletonMap(
            ERROR_KEY,
            "Incorrect credentials!");

    String BAD_STATUS_MESSAGE = "Entity cannot be changed due to its status";
    String BAD_GENRE_MESSAGE = "At least one of the genres by these IDs does not exist or is deleted";
    String BAD_BRAND_MESSAGE = "Guitar manufacturer by this ID does not exist or is deleted";
    String BAD_GUITAR_MESSAGE = "There is no guitar in price-list by this ID";
    String BAD_CUSTOMER_MESSAGE = "Customer account by this ID does not exist or is deleted";
    String BAD_INSTOCK_MESSAGE = "There is no available for order instock good by this ID";
    String NOT_VERIFIED_MESSAGE = "You did non pass email verification or Your email verification code is wrong";
    String ALREADY_DELETED_MESSAGE = "Object by this ID is already deleted";
    String FORBIDDEN_MESSAGE = "Authorization check failed";
    String NOT_FOUND_MESSAGE = "Wrong ID, object not found";
    String BAD_LOGIN_MESSAGE = "Incorrect credentials!";

    String USER_NOT_FOUND = "User not found!";
    String ORDER_STATUS = "Order Status";
    String GOOD_STATUS = "Good Status";
    String DELETED_STATUS = "isDeleted";
    String VERIFIED = "Verification successfull!";
}
