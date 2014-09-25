package uk.co.revsys.objectology.action.handler.exception;

public class InvalidStateException extends ActionInvocationException{

    public InvalidStateException(String message) {
        super(message);
    }

    public InvalidStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidStateException(Throwable cause) {
        super(cause);
    }

}
