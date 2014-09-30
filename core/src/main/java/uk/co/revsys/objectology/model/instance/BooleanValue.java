package uk.co.revsys.objectology.model.instance;

import java.text.ParseException;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.template.BooleanTemplate;

public class BooleanValue extends AtomicAttribute<BooleanValue, BooleanTemplate, java.lang.Boolean>{

    public BooleanValue() {
    }

    public BooleanValue(String value) throws ValidationException, ParseException {
        super(value);
    }

    public BooleanValue(java.lang.Boolean value) throws ValidationException {
        super(value);
    }

    @Override
    public java.lang.Boolean parseValueFromString(String value) throws ParseException {
        return java.lang.Boolean.valueOf(value);
    }

    @Override
    public BooleanValue copy() {
        try {
            return new BooleanValue(getValue());
        } catch (ValidationException ex) {
            // Should never be thrown
            throw new RuntimeException(ex);
        }
    }

}
