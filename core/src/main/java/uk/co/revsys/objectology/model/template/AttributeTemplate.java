package uk.co.revsys.objectology.model.template;

import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.ObjectWithNature;

public interface AttributeTemplate<A extends Attribute> extends ObjectWithNature{
	
	public A getValue();
	
	public void setValue(A value);
	
	public Class<? extends A> getAttributeType();
	
}
