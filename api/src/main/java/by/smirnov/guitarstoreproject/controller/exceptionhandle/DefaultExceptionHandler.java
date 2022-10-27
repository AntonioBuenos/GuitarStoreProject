package by.smirnov.guitarstoreproject.controller.exceptionhandle;

import by.smirnov.guitarstoreproject.exception.NoSuchEntityException;
import by.smirnov.guitarstoreproject.util.UUIDGenerator;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

import static by.smirnov.guitarstoreproject.constants.ResponseEntityConstants.ERROR_KEY;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler({NoSuchEntityException.class, EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleEntityNotFountException(Exception e) {

        ErrorContainer error = ErrorContainer
                .builder()
                .exceptionId(UUIDGenerator.generateUUID())
                .errorCode(2)
                .errorMessage(e.getMessage())
                .e(e.getClass().toString())
                .build();

        return new ResponseEntity<>(Collections.singletonMap(ERROR_KEY, error), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NumberFormatException.class})
    public ResponseEntity<Object> handleWrongNumberFormatException(Exception e) {

        ErrorContainer error = ErrorContainer
                .builder()
                .exceptionId(UUIDGenerator.generateUUID())
                .errorCode(3)
                .errorMessage(e.getMessage())
                .e(e.getClass().toString())
                .build();

        return new ResponseEntity<>(Collections.singletonMap("error", error), HttpStatus.NOT_FOUND);
    }
}
