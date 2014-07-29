package uk.co.revsys.objectology.action.handler;

import uk.co.revsys.objectology.action.handler.ActionInvocationException;

public class MissingParameterException extends ActionInvocationException{

    public MissingParameterException(String message) {
        super(message);
    }

    public MissingParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingParameterException(Throwable cause) {
        super(cause);
    }

}
