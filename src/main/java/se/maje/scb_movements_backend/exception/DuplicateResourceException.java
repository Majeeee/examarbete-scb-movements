package se.maje.scb_movements_backend.exception;

public class DuplicateResourceException extends ApiException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}