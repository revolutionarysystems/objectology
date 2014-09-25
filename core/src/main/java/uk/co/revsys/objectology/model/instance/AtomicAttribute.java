package uk.co.revsys.objectology.model.instance;

import com.fasterxml.jackson.annotation.JsonValue;
import java.text.ParseException;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.template.AtomicAttributeTemplate;

public abstract class AtomicAttribute<T extends AtomicAttributeTemplate, V extends Object> extends AbstractAttribute<T> {
    
    private V value;
    
    public AtomicAttribute() {
    }
    
    public AtomicAttribute(String value) throws ValidationException, ParseException{
        setValue(parseValueFromString(value));
    }
    
    public AtomicAttribute(V value) throws ValidationException{
        setValue(value);
    }
    
    public V getValue() {
        return value;
    }
    
    public void setValue(V value) throws ValidationException {
        this.value = value;
        validate();
    }
    
    public abstract V parseValueFromString(String value) throws ParseException;
    
    @Override
    @JsonValue
    public String toString() {
        V value = getValue();
        if (value == null) {
            return "";
        }
        return getValue().toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AtomicAttribute) {
            obj = ((AtomicAttribute) obj).getValue();
        }
        if (getValue() == null) {
            return obj == null;
        }
        return getValue().equals(obj);
    }
    
}
