package uk.co.revsys.objectology.model.template;

import uk.co.revsys.objectology.model.instance.Attribute;

public abstract class AbstractAttributeTemplate<A extends Attribute> implements AttributeTemplate<A>{

	private A value;
	
	@Override
	public A getValue() {
		return value;
	}

	@Override
	public void setValue(A value) {
		this.value = value;
	}

}
