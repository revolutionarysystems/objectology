package uk.co.revsys.objectology.model.template;

import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.ObjectWithNature;

public interface AttributeTemplate<A extends Attribute> extends ObjectWithNature{
	
	public Class<? extends A> getAttributeType();
    
    public void validate(A attribute) throws ValidationException;
    
    public boolean isStatic();
    
    public A getValue();
    
    public A createDefaultInstance();
	
}
