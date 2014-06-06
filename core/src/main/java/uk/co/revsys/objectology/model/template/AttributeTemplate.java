package uk.co.revsys.objectology.model.template;

import uk.co.revsys.objectology.model.instance.Attribute;

public interface AttributeTemplate<A extends Attribute> {
	
	public A getValue();
	
	public void setValue(A value);
	
	public Class<? extends A> getAttributeType();
	
}
