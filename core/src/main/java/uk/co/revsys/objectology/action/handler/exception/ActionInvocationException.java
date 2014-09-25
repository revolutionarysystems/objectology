package uk.co.revsys.objectology.action.handler.exception;

public class ActionInvocationException extends Exception{

    public ActionInvocationException(String message) {
        super(message);
    }

    public ActionInvocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActionInvocationException(Throwable cause) {
        super(cause);
    }

}
