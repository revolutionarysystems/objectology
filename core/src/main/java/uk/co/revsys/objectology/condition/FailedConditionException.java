package uk.co.revsys.objectology.condition;

public class FailedConditionException extends Exception{

    public FailedConditionException(String message) {
        super(message);
    }

    public FailedConditionException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedConditionException(Throwable cause) {
        super(cause);
    }

}
