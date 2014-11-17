package uk.co.revsys.objectology.model.instance;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.text.ParseException;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.mapping.json.deserialise.BooleanValueDeserialiser;
import uk.co.revsys.objectology.model.template.BooleanTemplate;

@JsonDeserialize(using=BooleanValueDeserialiser.class)
public class BooleanValue extends AtomicAttribute<BooleanValue, BooleanTemplate, java.lang.Boolean>{

    public BooleanValue() {
    }

    public BooleanValue(String value) throws ValidationException, ParseException {
        super(value);
    }

    public BooleanValue(java.lang.Boolean value) throws ValidationException {
        super(value);
    }
    
    public BooleanValue(boolean value) throws ValidationException{
        super(value);
    }
    
    @Override
    public java.lang.Boolean parseValueFromString(String value) throws ParseException {
        return java.lang.Boolean.valueOf(value);
    }

}
