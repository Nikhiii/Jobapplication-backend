package com.example.springapp.exception;

// import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
    

    public class TooManyApplicationsException extends RuntimeException {
        public TooManyApplicationsException(String message) {
            super(message);
        }
    }
}

// @ControllerAdvice
// public class GlobalExceptionHandler {
//     @ExceptionHandler(ResourceNotFoundException.class)
//     public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
//         // Customize error response
//     }
// }
