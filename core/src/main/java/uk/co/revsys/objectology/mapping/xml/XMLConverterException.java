package uk.co.revsys.objectology.mapping.xml;

import uk.co.revsys.objectology.mapping.DeserialiserException;

public class XMLConverterException extends DeserialiserException{

    public XMLConverterException() {
    }

    public XMLConverterException(String message) {
        super(message);
    }

    public XMLConverterException(String message, Throwable cause) {
        super(message, cause);
    }

    public XMLConverterException(Throwable cause) {
        super(cause);
    }

}
