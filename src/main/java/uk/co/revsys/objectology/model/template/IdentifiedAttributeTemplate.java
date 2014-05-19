package uk.co.revsys.objectology.model.template;

import uk.co.revsys.objectology.model.instance.Attribute;

public interface IdentifiedAttributeTemplate<A extends Attribute> extends AttributeTemplate<A>{

	public void setId(String id);
	
	public String getId();
	
}
