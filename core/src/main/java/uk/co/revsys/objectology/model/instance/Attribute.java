package uk.co.revsys.objectology.model.instance;

import uk.co.revsys.objectology.exception.ValidationException;
import uk.co.revsys.objectology.model.template.AttributeTemplate;

public interface Attribute<A extends Attribute, T extends AttributeTemplate>{

	public void setTemplate(T template) throws ValidationException;
	
	public T getTemplate();
    
    public void setParent(OlogyInstance parent);
    
    public OlogyInstance getParent();
    
    public void validate() throws ValidationException;
    
    public A copy();
	
}
