package edu.miu.cs489.tsogt.backend.advice;

import edu.miu.cs489.tsogt.backend.exception.transaction.RequestAlreadySentException;
import edu.miu.cs489.tsogt.backend.exception.user.NonDuplicateCheckRegister;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class BackendWebAPIExceptionHandler {

    @ExceptionHandler(NonDuplicateCheckRegister.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handlePublisherNotFoundException(NonDuplicateCheckRegister nonDuplicateCheckRegister) {
        Map<String, String> errorMessageMap = new HashMap<>();
        errorMessageMap.put("errorMessage", nonDuplicateCheckRegister.getMessage());
        return errorMessageMap;
    }
    @ExceptionHandler(RequestAlreadySentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> RequestAlreadySentException(RequestAlreadySentException requestAlreadySentException) {
        Map<String, String> errorMessageMap = new HashMap<>();
        errorMessageMap.put("errorMessage", requestAlreadySentException.getMessage());
        return errorMessageMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BadCredentialsException.class)
    public Map<String, String> handleUserAuthBadCredentialException(BadCredentialsException bcEx) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", bcEx.getMessage());
        errorMap.put("errorDisplayText", "Invalid Username and/or Password!");
        return errorMap;
    }
}
