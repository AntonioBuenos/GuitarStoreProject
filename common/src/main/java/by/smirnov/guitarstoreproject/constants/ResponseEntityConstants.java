package by.smirnov.guitarstoreproject.constants;

import java.util.Collections;
import java.util.Map;

public interface ResponseEntityConstants {
    String ERROR_KEY = "Error Message";
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
