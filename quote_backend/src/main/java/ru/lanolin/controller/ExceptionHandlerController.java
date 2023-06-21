package ru.lanolin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.lanolin.domain.StatusMessage;
import ru.lanolin.exceptions.ResponseException;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
//    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex,
//                                                                          HttpHeaders headers,
//                                                                          HttpStatus status,
//                                                                          WebRequest request) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return ResponseEntity.badRequest().body(errors);
//    }

    @ExceptionHandler(value = {ResponseException.class})
    public ResponseEntity<StatusMessage> handleResponseException(ResponseException ex) {
        return new ResponseEntity<>(ex.getStatusMessage(), ex.getStatus());
    }

}
