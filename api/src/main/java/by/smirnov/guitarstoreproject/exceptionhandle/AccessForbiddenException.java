package by.smirnov.guitarstoreproject.exceptionhandle;

import static by.smirnov.guitarstoreproject.constants.ResponseEntityConstants.FORBIDDEN_MESSAGE;

public class AccessForbiddenException extends RuntimeException{

    public AccessForbiddenException(String message) {
        super(message);
    }

    public AccessForbiddenException() {
        super(FORBIDDEN_MESSAGE);
    }

    @Override
    public String toString() {
        return "AccessForbiddenException{}" + super.toString();
    }
}
