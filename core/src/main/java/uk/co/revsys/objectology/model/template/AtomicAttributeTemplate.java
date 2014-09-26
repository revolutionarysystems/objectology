package uk.co.revsys.objectology.model.template;

import uk.co.revsys.objectology.exception.RequiredAttributeException;
import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.instance.AtomicAttribute;

public abstract class AtomicAttributeTemplate<A extends AtomicAttribute> extends AbstractAttributeTemplate<A> {

    private boolean required;

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
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
