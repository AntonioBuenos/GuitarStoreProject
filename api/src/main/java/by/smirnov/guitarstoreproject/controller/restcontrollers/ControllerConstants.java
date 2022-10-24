package by.smirnov.guitarstoreproject.controller.restcontrollers;

import java.util.Collections;
import java.util.Map;

public interface ControllerConstants {

    String ERROR_KEY = "Error Message";
    Map<String, String> NOT_FOUND_MAP = Collections.singletonMap(ERROR_KEY, "Wrong ID, object not found");
    Map<String, String> FORBIDDEN_MAP = Collections.singletonMap(ERROR_KEY, "Authorization check failed");
    Map<String, String> ALREADY_DELETED_MAP = Collections.singletonMap(ERROR_KEY, "Object by this ID is already deleted");
}
