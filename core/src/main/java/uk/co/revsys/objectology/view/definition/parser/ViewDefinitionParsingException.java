package uk.co.revsys.objectology.view.definition.parser;

public class ViewDefinitionParsingException extends Exception{

    public ViewDefinitionParsingException(String message) {
        super(message);
    }

    public ViewDefinitionParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ViewDefinitionParsingException(Throwable cause) {
        super(cause);
    }

}
