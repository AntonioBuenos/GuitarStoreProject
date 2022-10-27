package by.smirnov.guitarstoreproject.controller.exceptionhandle;

public class BadRequestException extends RuntimeException {

    public BadRequestException (String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "BadRequestException{}" + super.toString();
    }
}
