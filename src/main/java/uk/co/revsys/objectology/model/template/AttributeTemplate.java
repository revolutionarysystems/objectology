package uk.co.revsys.objectology.model.template;

import uk.co.revsys.objectology.model.instance.Attribute;

public interface AttributeTemplate {

	public abstract Class<? extends Attribute> getAttributeType();
	
}
