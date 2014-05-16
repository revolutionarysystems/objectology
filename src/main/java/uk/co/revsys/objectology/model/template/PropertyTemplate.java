package uk.co.revsys.objectology.model.template;

import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.Property;

public class PropertyTemplate extends AtomicAttributeTemplate{

	@Override
	public Class<? extends Attribute> getAttributeType() {
		return Property.class;
	}

}
