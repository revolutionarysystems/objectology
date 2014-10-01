package uk.co.revsys.objectology.model.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.instance.Attribute;

public abstract class AbstractAttributeTemplate<A extends Attribute> implements AttributeTemplate<A>{

    @JsonProperty("static")
    private boolean isStatic = false;
    private A value;
    
    @Override
    public void validate(A attribute) throws ValidationException {
        
    }

    @Override
    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    @Override
    public A getValue() {
        return value;
    }

    public void setValue(A value) {
        this.value = value;
    }

}
