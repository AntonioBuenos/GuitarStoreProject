package by.smirnov.guitarstoreproject.validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static by.smirnov.guitarstoreproject.validation.ValidationConstants.ERROR;

public class ValidationErrorConverter {

    private ValidationErrorConverter() {}

    public static ResponseEntity<Object> getErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + ERROR,
                FieldError::getDefaultMessage
        );

        Map<String, String> errorsMap = bindingResult.getFieldErrors().stream().collect(collector);
        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
    }
}
