package uk.co.revsys.objectology.model.instance;

import uk.co.revsys.objectology.model.template.AttributeTemplate;

public interface Attribute<T extends AttributeTemplate> {

	public void setTemplate(T template);
	
	public T getTemplate();
    
    public void setParent(OlogyInstance parent);
    
    public OlogyInstance getParent();
	
}
