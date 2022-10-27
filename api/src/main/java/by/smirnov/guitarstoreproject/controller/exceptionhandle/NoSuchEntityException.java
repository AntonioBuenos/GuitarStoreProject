package by.smirnov.guitarstoreproject.controller.exceptionhandle;

import static by.smirnov.guitarstoreproject.constants.ResponseEntityConstants.NOT_FOUND_MESSAGE;

public class NoSuchEntityException extends RuntimeException {

    public NoSuchEntityException(String message) {
        super(message);
    }

    public NoSuchEntityException() {
        super(NOT_FOUND_MESSAGE);
    }

    @Override
    public String toString() {
        return "NoSuchEntityException{}" + super.toString();
    }
}
