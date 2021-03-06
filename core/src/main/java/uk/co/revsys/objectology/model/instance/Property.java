package uk.co.revsys.objectology.model.instance;

import java.text.ParseException;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.template.PropertyTemplate;

public class Property extends AtomicAttribute<Property, PropertyTemplate, String> {

    public Property() {
    }

    public Property(String value) throws ValidationException {
        setValue(value);
    }

    @Override
    public String parseValueFromString(String value) throws ParseException {
        return value;
    }

}
