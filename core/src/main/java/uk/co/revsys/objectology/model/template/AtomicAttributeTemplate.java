package uk.co.revsys.objectology.model.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import uk.co.revsys.objectology.exception.RequiredAttributeException;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.instance.AtomicAttribute;

public abstract class AtomicAttributeTemplate<A extends AtomicAttribute> extends AbstractAttributeTemplate<A> {

    private boolean required;
    @JsonProperty("default")
    private String rawDefaultValue;

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getRawDefaultValue() {
        return rawDefaultValue;
    }

    public void setRawDefaultValue(String rawDefaultValue) throws ValidationException, ParseException{
        this.rawDefaultValue = rawDefaultValue;
        // Validate value
        createInstance(rawDefaultValue);
    }

    @Override
    public A createDefaultInstance() {
        try {
            return createInstance(rawDefaultValue);
        } catch (ValidationException ex) {
            // Should never be thrown
            throw new RuntimeException(ex);
        } catch (ParseException ex) {
            // Should never be thrown
            throw new RuntimeException(ex);
        }
    }
    
    protected A createInstance(String value) throws ValidationException, ParseException{
        try {
            return (A) getAttributeType().getConstructor(String.class).newInstance(value);
        } catch (NoSuchMethodException ex) {
            // Should never be thrown
            throw new RuntimeException(ex);
        } catch (SecurityException ex) {
            // Should never be thrown
            throw new RuntimeException(ex);
        } catch (InstantiationException ex) {
            // Should never be thrown
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            // Should never be thrown
            throw new RuntimeException(ex);
        } catch (IllegalArgumentException ex) {
            // Should never be thrown
            throw new RuntimeException(ex);
        } catch (InvocationTargetException ex) {
            if(ex.getTargetException() instanceof ValidationException){
                throw (ValidationException)ex.getTargetException();
            }else if(ex.getTargetException() instanceof ParseException){
                throw (ParseException)ex.getTargetException();
            }
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void validate(A attribute) throws ValidationException {
        super.validate(attribute);
        if(isRequired() && attribute.getValue() == null){
            throw new RequiredAttributeException("Required Attribute");
        }
        doValidate(attribute);
    }
    
    public void doValidate(A attribute) throws ValidationException{
        
    }

}
