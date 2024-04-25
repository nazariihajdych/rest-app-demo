package ua.restapp.demo.exception;

public class InconsistencyException extends RuntimeException{
    public InconsistencyException(String message) {
        super(message);
    }
}
