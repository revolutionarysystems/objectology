package uk.co.revsys.objectology.model.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.GeneratedAttribute;

public abstract class AbstractAttributeTemplate<A extends Attribute> implements AttributeTemplate<A>{

    @JsonProperty("static")
    private boolean isStatic = false;
	private A value;
	
	@Override
	public A getValue() {
        if(GeneratedAttribute.class.isAssignableFrom(getAttributeType())){
            return null;
        }
        if(value == null){
            return newInstance();
        }
		return value;
	}

	@Override
	public void setValue(A value) {
		this.value = value;
	}
    
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

}
