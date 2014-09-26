package uk.co.revsys.objectology.exception;

public class RequiredAttributeException extends ValidationException{

    public RequiredAttributeException(String message) {
        super(message);
    }

    public RequiredAttributeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequiredAttributeException(Throwable cause) {
        super(cause);
    }

}
