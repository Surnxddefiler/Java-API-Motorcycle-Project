package Garage.Motorcycle.web;

import Garage.Motorcycle.customExeptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    //creating Logger
    private static final Logger log=LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //global handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception e){
        log.error("Exception happened", e);
        ErrorResponse response=new ErrorResponse("Server Error", e.getMessage(), LocalDateTime.now());
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    };
    //custom Error handler bike not found
    @ExceptionHandler(MotorcycleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMotorcycleNotFound(Exception e){
        log.error("Exception Motorcycle Not Found");
        ErrorResponse response=new ErrorResponse("Motorcycle not found", e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    };
    //user not found handler
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(Exception e){
        log.error("Exception User Not Found");
        ErrorResponse response=new ErrorResponse("User not found", e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    };
    //invalid page size handler
    @ExceptionHandler(InvalidPageSize.class)
    public ResponseEntity<ErrorResponse> handleInvalidPageSize(Exception e){
        log.error("invalid page size");
        ErrorResponse response=new ErrorResponse("Invalid page size", e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    //invalid motorcycle year handler
    @ExceptionHandler(InvalidYear.class)
    public ResponseEntity<ErrorResponse> handleInvalidYear(Exception e){
        log.error("invalid year");
        ErrorResponse response=new ErrorResponse("Invalid year", e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    //exists email for user handler
    @ExceptionHandler(EmailExists.class)
    public ResponseEntity<ErrorResponse> handleEmailExists(Exception e){
        log.error("email exists");
        ErrorResponse response=new ErrorResponse("Email Exists", e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    //not found Service record
    @ExceptionHandler(ServiceRecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleServiceRecordNotFound(Exception e){
        log.error("Service Not Found");
        ErrorResponse response=new ErrorResponse("Service Not found", e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    //Validation Error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");


        ErrorResponse response = new ErrorResponse("VALIDATION_ERROR", message, LocalDateTime.now());

        return ResponseEntity.badRequest().body(response);
    }
    //json parse error
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonParseException(HttpMessageNotReadableException e) {

        ErrorResponse response = new ErrorResponse(
                "Json parse error",
                "Invalid request body",
                LocalDateTime.now()
        );

        return ResponseEntity.badRequest().body(response);
    }
    //wrong password
    @ExceptionHandler(WrongPassword.class)
    public ResponseEntity<ErrorResponse> wrongPasswordException(Exception e){
        log.error("incorrect password");
        ErrorResponse response=new ErrorResponse("wrong password", e.getMessage(), LocalDateTime.now());
        return ResponseEntity.badRequest().body(response);
    }

}
