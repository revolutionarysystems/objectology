package uk.co.revsys.objectology.model.template;

import uk.co.revsys.objectology.model.instance.Attribute;
import uk.co.revsys.objectology.model.instance.Link;

public class LinkTemplate implements AttributeTemplate{

	@Override
	public Class<? extends Attribute> getAttributeType() {
		return Link.class;
	}

}
