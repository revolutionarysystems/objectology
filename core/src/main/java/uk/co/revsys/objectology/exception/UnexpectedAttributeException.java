package uk.co.revsys.objectology.exception;

public class UnexpectedAttributeException extends Exception{

    public UnexpectedAttributeException(String message) {
        super(message);
    }

    public UnexpectedAttributeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnexpectedAttributeException(Throwable cause) {
        super(cause);
    }

}
