package by.smirnov.guitarstoreproject.controller.restcontrollers;

import java.util.Collections;
import java.util.Map;

import static by.smirnov.guitarstoreproject.constants.CommonConstants.ERROR_KEY;

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
}
