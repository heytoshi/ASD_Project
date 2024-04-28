package edu.miu.cs489.tsogt.backend.exception.transaction;

public class RequestAlreadySentException extends Exception {
    public RequestAlreadySentException(String message) {
        super(message);
    }
}
